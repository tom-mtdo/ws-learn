define([
    'utilities',
    'require',
    'configuration',
//    'text!../../../../templates/desktop/booking-confirmation.html',
    'text!../../../../templates/desktop/create-booking.html',
    'text!../../../../templates/desktop/ticket-categories.html',
    'text!../../../../templates/desktop/ticket-summary-view.html',
    'bootstrap'
],function (
    utilities,
    require,
    config,
//    bookingConfirmationTemplate,
    createBookingTemplate,
    ticketEntriesTemplate,
    ticketSummaryViewTemplate
){
	
	// input: model like creating booking: model{showId, PerformanceId, bookingRequest (which was initiated as tickets:[])}
	var TicketSummaryView = Backbone.View.extend({
        tagName:'tr',
        events:{
            "click i":"removeEntry"
        },
        render:function () {
            var self = this;
            utilities.applyTemplate($(this.el), ticketSummaryViewTemplate, this.model.bookingRequest);
        },
        removeEntry:function () {
            this.model.bookingRequest.tickets.splice(this.model.index, 1);
        }
    });

	// input: model{showId, PerformanceId, bookingRequest (which was initiated as tickets:[])}
	// input: el
	var CreateBookingView = Backbone.View.extend({		
		
		intervalDuration : 100,
        formValues : [],        
		events:{
            "click input[name='submit']":"save",
            "change select[id='sectionSelect']":"refreshPrices",
            "keyup #email":"updateEmail",
            "change #email":"updateEmail",
            "click input[name='add']":"addQuantities",
            "click i":"updateQuantities"
        },
		render:function (){
			var self = this;
			// get show info
			$.getJSON(config.baseUrl + "rest/shows/" + this.model.showId, function (selectedShow) {

                // get performance
				self.currentPerformance = _.find(selectedShow.performances, function (item) {
                    return item.id == self.model.performanceId;
                });
                
				// store show id?
                var id = function (item) {return item.id;};
                // prepare a list of sections to populate the dropdown
                var sections = _.uniq(_.sortBy(_.pluck(selectedShow.ticketPrices, 'section'), id), true, id);
                utilities.applyTemplate($(self.el), createBookingTemplate, {
                    sections:sections,
                    show:selectedShow,
                    performance:self.currentPerformance});
                self.ticketCategoriesView = new TicketCategoriesView({model:{}, el:$("#ticketCategoriesViewPlaceholder") });
                self.ticketSummaryView = new TicketSummaryView({model:self.model, el:$("#ticketSummaryView")});
                self.show = selectedShow;
                self.ticketCategoriesView.render();
                self.ticketSummaryView.render();
                $("#sectionSelector").change();
//                self.watchForm();
            });
            return this;
		},
		refreshPrices:function (event) {            
			// get all prices, categories, (.e.g: adult, children) for selected section.
			var ticketPrices = _.filter(this.show.ticketPrices, function (item) {
                return item.section.id == event.currentTarget.value;
            });
			
            // get descriptions of each price (or category)
			var sortedTicketPrices = _.sortBy(ticketPrices, function(ticketPrice) {
                return ticketPrice.ticketCategory.description;
            });
			// get all prices
            var ticketPriceInputs = new Array();
            _.each(sortedTicketPrices, function (ticketPrice) {
                ticketPriceInputs.push({ticketPrice:ticketPrice});
            });
            // pass all prices to template
            this.ticketCategoriesView.model = ticketPriceInputs;
            this.ticketCategoriesView.render();
        },
	    addQuantities:function () {
	        var self = this;
	        _.each(this.ticketCategoriesView.model, function (model) {
	            if (model.quantity != undefined) {
	                var found = false;
	                _.each(self.model.bookingRequest.tickets, function (ticket) {
	                    if (ticket.ticketPrice.id == model.ticketPrice.id) {
	                        ticket.quantity += model.quantity;
	                        found = true;
	                    }
	                });
	                if (!found) {
	                    self.model.bookingRequest.tickets.push({ticketPrice:model.ticketPrice, quantity:model.quantity});
	                }
	            }
	        });
	        this.ticketCategoriesView.model = null;
	        $('option:selected', 'select').removeAttr('selected');
	        this.ticketCategoriesView.render();
	        this.updateQuantities();
	    },
	    updateQuantities:function () {
	        // make sure that tickets are sorted by section and ticket category
	        this.model.bookingRequest.tickets.sort(function (t1, t2) {
	            if (t1.ticketPrice.section.id != t2.ticketPrice.section.id) {
	                return t1.ticketPrice.section.id - t2.ticketPrice.section.id;
	            }
	            else {
	                return t1.ticketPrice.ticketCategory.id - t2.ticketPrice.ticketCategory.id;
	            }
	        });
	
	        this.model.bookingRequest.totals = _.reduce(this.model.bookingRequest.tickets, function (totals, ticketRequest) {
	            return {
	                tickets:totals.tickets + ticketRequest.quantity,
	                price:totals.price + ticketRequest.quantity * ticketRequest.ticketPrice.price
	            };
	        }, {tickets:0, price:0.0});
	
	        this.ticketSummaryView.render();
	        this.setCheckoutStatus();
	    },
	    setCheckoutStatus:function () {
	        if (this.model.bookingRequest.totals != undefined && this.model.bookingRequest.totals.tickets > 0 && this.model.bookingRequest.email != undefined && this.model.bookingRequest.email != '') {
	            $('input[name="submit"]').removeAttr('disabled');
	        }
	        else {
	            $('input[name="submit"]').attr('disabled', true);
	        }
	    },
	    updateEmail:function (event) {
            if ($(event.currentTarget).is(':valid')) {
                this.model.bookingRequest.email = event.currentTarget.value;
                $("#error-email").empty();
            } else {
                $("#error-email").empty().append("Please enter a valid e-mail address");
                delete this.model.bookingRequest.email;
            }
            this.setCheckoutStatus();
        },
        save:function (event) {
            var bookingRequest = {ticketRequests:[]};
            var self = this;
            bookingRequest.ticketRequests = _.map(this.model.bookingRequest.tickets, function (ticket) {
                return {ticketPrice:ticket.ticketPrice.id, quantity:ticket.quantity}
            });
            bookingRequest.email = this.model.bookingRequest.email;
            bookingRequest.performance = this.model.performanceId
            $("input[name='submit']").attr("disabled", true)
            $.ajax({url: (config.baseUrl + "rest/bookings"),
                data:JSON.stringify(bookingRequest),
                type:"POST",
                dataType:"json",
                contentType:"application/json",
                success:function (booking) {
                    this.model = {}
                    $.getJSON(config.baseUrl +'rest/shows/performance/' + booking.performance.id, function (retrievedPerformance) {
                        utilities.applyTemplate($(self.el), bookingConfirmationTemplate, {booking:booking, performance:retrievedPerformance })
                    });
                }}).error(function (error) {
                    if (error.status == 400 || error.status == 409) {
                        var errors = $.parseJSON(error.responseText).errors;
                        _.each(errors, function (errorMessage) {
                            $("#request-summary").append('<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><strong>Error!</strong> ' + errorMessage + '</div>')
                        });
                    } else {
                        $("#request-summary").append('<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><strong>Error! </strong>An error has occured</div>')
                    }
                    $("input[name='submit']").removeAttr("disabled");
                })

        },
    });
	
	// input: a model, which is a set of ticketPrices
	var TicketCategoriesView = Backbone.View.extend({
        id:'categoriesView',
//        intervalDuration : 100,
//        formValues : [],
        events:{
            "change input":"onChange"
        },
        render:function () {
            if (this.model != null) {
                var ticketPrices = _.map(this.model, function (item) {
                    return item.ticketPrice;
                });
                utilities.applyTemplate($(this.el), ticketEntriesTemplate, {ticketPrices:ticketPrices});
            } else {
                $(this.el).empty();
            }
//            this.watchForm();
            return this;
        }
	,
        onChange:function (event) {
        	// number of tickets
        	var value = event.currentTarget.value;	
            // for which category
        	var ticketPriceId = $(event.currentTarget).data("tm-id");
            var modifiedModelEntry = _.find(this.model, function (item) {
                return item.ticketPrice.id == ticketPriceId
            });
            // update model
            if ($.isNumeric(value) && value > 0) {
                modifiedModelEntry.quantity = parseInt(value);
            }
            else {
                delete modifiedModelEntry.quantity;
            }
            // display error messages
            if (value.length > 0 &&
                   (!$.isNumeric(value)  // is a non-number, other than empty string
                        || value <= 0 // is negative
                        || parseFloat(value) != parseInt(value))) { // is not an integer
                $("#error-input-"+ticketPriceId).empty().append("Please enter a positive integer value");
                $("#ticket-category-fieldset-"+ticketPriceId).addClass("error");
            } else {
                $("#error-input-"+ticketPriceId).empty();
                $("#ticket-category-fieldset-"+ticketPriceId).removeClass("error");
            }
            // are there any outstanding errors after this update?
            // if yes, disable the input button
            if (
               $("div[id^='ticket-category-fieldset-']").hasClass("error") ||
                   _.isUndefined(modifiedModelEntry.quantity) ) {
              $("input[name='add']").attr("disabled", true)
            } else {
              $("input[name='add']").removeAttr("disabled")
            }
        }
	
//	,
//        watchForm: function() {
//            if($("#sectionSelectorPlaceholder").length) {
//                var self = this;
//                $("input[name*='tickets']").each( function(index,element) {
//                    if(element.value !== self.formValues[element.name]) {
//                        self.formValues[element.name] = element.value;
//                        $("input[name='"+element.name+"']").change();
//                    }
//                });
//                this.timerObject = setTimeout(function() {
//                    self.watchForm();
//                }, this.intervalDuration);
//            } else {
//                this.onClose();
//            }
//        }
//        ,
//        onClose: function() {
//            if(this.timerObject) {
//                clearTimeout(this.timerObject);
//                delete this.timerObject;
//            }
//        }
    });
    
    return CreateBookingView;
});