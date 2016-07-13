define([
    'utilities',
    'bootstrap',
    'text!../../../../templates/desktop/events.html'
], function (
    utilities,
    bootstrap,
    eventsTemplate) {

    var EventsView = Backbone.View.extend({
//        events:{
//            "click a":"update"
//        },
        render:function () {
//            utilities.applyTemplate($(this.el), eventsTemplate)
//        	var categories = [{id:'1', description:'Movie'}, {id:'2', description:'Music'}];
          var categories = _.uniq(
          _.map(this.model.models, function(model){
              return model.get('category')
          }), false, function(item){
              return item.id
          });

        	utilities.applyTemplate($(this.el), eventsTemplate, {categories: categories, model:this.model})
        	$(".carousel").carousel();
        	$(this.el).find('.item:first').addClass('active');
            return this;
        },
        
//        render:function () {
//            var categories = _.uniq(
//                _.map(this.model.models, function(model){
//                    return model.get('category')
//                }), false, function(item){
//                    return item.id
//                });
//            utilities.applyTemplate($(this.el), eventsTemplate, {categories:categories, model:this.model})
//            $(this.el).find('.item:first').addClass('active');
//            $(".carousel").carousel();
//            $("a[rel='popover']").popover({trigger:'hover',container:'#content'});
//            return this;
//        },
//        update:function () {
//            $("a[rel='popover']").popover('hide')
//        }
    });
    
    

    return  EventsView;
});