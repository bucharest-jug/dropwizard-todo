require([
    'underscore', 'jquery', 'backbone'
], function (_, $, Backbone) {

    // add close method to all views for clean-up
    Backbone.View.prototype.close = function () {
        // call user defined close method if exists
        if (this.beforeClose) {
            this.beforeClose()
        }
        for (var index in this._periodicFunctions) {
            clearInterval(this._periodicFunctions[index])
        }
        this.remove()
        this.unbind()
    }

    /**
     * Use callPeriodically to register functions that poll the server periodically and update your view.
     * They will automatically stop when the view close method is invoked.
     */
    Backbone.View.prototype.callPeriodically = function (callback, interval) {
        if (!this._periodicFunctions) {
            this._periodicFunctions = []

        }
        this._periodicFunctions.push(setInterval(callback, interval))
    }

    var AppRouter = Backbone.Router.extend({
        routes:{
            '*path':'defaultRoute'
        },
        showView:function (selector, view) {
            // close the previous view - does binding clean-up and avoids memory leaks
            if (this.currentView) this.currentView.close()
            // render the view inside the selector element
            $(selector).html(view.render().el)
            this.currentView = view
            return view
        },
        defaultRoute:function () {
        }
    });

    var app = new AppRouter()
    Backbone.history.start()
})
