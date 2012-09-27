define([
    "underscore", "backbone", "model/todo", "text!tpl/todo-table.html"
], function (_, Backbone, Todo, Table) {

    var TodoListView = Backbone.View.extend({
        tagName:"div",
        className:"row",
        events:{
            "click .delete":"deleteTodo"
        },
        initialize:function () {
            this.$el.html(Table)
        },
        beforeClose:function () {

        },
        render:function () {
            return this;
        },
        deleteTodo:function (eventName) {

        }
    })

    return TodoListView
})