define([
    'underscore', 'jquery', 'backbone', "model/todo", "view/todo-list"
], function (_, $, Backbone, Todo, TodoListView) {

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
            "":"todos",
            "todos/:id":"showTodo",
            "*path":"defaultRoute"
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
            this.todos()
        },
        todos:function () {
            var that = this,
                todos = new Todo.Collection
            // fetch the collection and update pass it to the view on success
            todos.fetch({success:function () {
                that.showView("#container", new TodoListView({
                    collection:todos
                }))
            }})
        },
        showTodo:function (id) {

        }
    })

    return AppRouter
})
