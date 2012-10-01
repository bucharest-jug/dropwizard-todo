define([
    "underscore", "backbone", "model/todo", "text!tpl/todo-table.html", "text!tpl/todo-row.html"
], function (_, Backbone, Todo, TableHTML, TableRowHTML) {

    var TodoListView = Backbone.View.extend({
        tagName:"div",
        className:"row",
        events:{
            "click .delete":"deleteTodo"
        },
        initialize:function () {
            this.$el.html(TableHTML)
        },
        beforeClose:function () {
            // remove any custom callbacks / child views
        },
        render:function () {
            var that = this,
                $tbody = this.$("tbody").empty(),
                template = _.template(TableRowHTML)

            this.collection.each(function (todo) {
                $tbody.append(template({
                    cid:todo.cid,
                    todoId:todo.get("id"),
                    email:todo.get("email"),
                    created:todo.get("created"),
                    count:todo.get("items").length
                }))
            })
            return this;
        },
        deleteTodo:function (eventName) {
            var cid = $(eventName.currentTarget).parents(".todo").attr("id")
            this.collection.getByCid(cid).destroy()
        }
    })

    return TodoListView
})