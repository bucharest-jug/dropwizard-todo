define([
    "underscore", "backbone", "model/todo", "view/todo-modal", "text!tpl/todo-table.html", "text!tpl/todo-row.html"
], function (_, Backbone, Todo, TodoModal, TableHTML, TableRowHTML) {

    var TodoListView = Backbone.View.extend({
        tagName:"div",
        className:"row",
        events:{
            "click .delete":"deleteTodo",
            "click .create":"showTodoModal"
        },
        initialize:function () {
            this.$el.html(TableHTML)
            this.collection.on("reset", this.render, this)
            var that = this
            // update the todo-list periodically from the server every 5 seconds as long as this view is active
            this.callPeriodically(function () {
                that.collection.fetch()
            }, 5000)
        },
        beforeClose:function () {
            // remove any custom callbacks / child views
            this.collection.off("reset", this.render)
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
        },
        showTodoModal:function () {
            // clean the old modal
            if (this.modal) this.modal.close()
            this.modal = new TodoModal({
                el:"#modal-container",
                model:new Todo.Model
            })
        }
    })

    return TodoListView
})