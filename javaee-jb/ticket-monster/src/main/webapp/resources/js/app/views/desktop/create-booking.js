define([
    'utilities',
    'require',
    'configuration',
//    'text!../../../../templates/desktop/booking-confirmation.html',
    'text!../../../../templates/desktop/create-booking.html',
//    'text!../../../../templates/desktop/ticket-categories.html',
//    'text!../../../../templates/desktop/ticket-summary-view.html',
    'bootstrap'
],function (
    utilities,
    require,
    config,
//    bookingConfirmationTemplate,
    createBookingTemplate
//    ticketEntriesTemplate,
//    ticketSummaryViewTemplate
){

	// input: model{showId, PerformanceId, bookingRequest{tickets:[]}}
	// input: el
	var CreateBookingView = Backbone.View.extend({		
		events:{
//            "click input[name='submit']":"save",
//            "change select[id='sectionSelect']":"refreshPrices",
//            "keyup #email":"updateEmail",
//            "change #email":"updateEmail",
//            "click input[name='add']":"addQuantities",
//            "click i":"updateQuantities"
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
//                self.ticketCategoriesView = new TicketCategoriesView({model:{}, el:$("#ticketCategoriesViewPlaceholder") });
//                self.ticketSummaryView = new TicketSummaryView({model:self.model, el:$("#ticketSummaryView")});
                self.show = selectedShow;
                self.ticketCategoriesView.render();
//                self.ticketSummaryView.render();
//                $("#sectionSelector").change();
//                self.watchForm();
            });
            return this;
		}
//        ,
//		refreshPrices:function (event) {
//            var ticketPrices = _.filter(this.show.ticketPrices, function (item) {
//                return item.section.id == event.currentTarget.value;
//            });
//            var sortedTicketPrices = _.sortBy(ticketPrices, function(ticketPrice) {
//                return ticketPrice.ticketCategory.description;
//            });
//            var ticketPriceInputs = new Array();
//            _.each(sortedTicketPrices, function (ticketPrice) {
//                ticketPriceInputs.push({ticketPrice:ticketPrice});
//            });
//            this.ticketCategoriesView.model = ticketPriceInputs;
//            this.ticketCategoriesView.render();
//        }
    });
    
    return CreateBookingView;
});