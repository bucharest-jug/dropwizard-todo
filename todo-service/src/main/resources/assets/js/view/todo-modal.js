define([
    "underscore", "jquery", "backbone", "text!tpl/todo-modal.html", "bootstrap"
], function (_, $, Backbone, TodoModalHtml) {

    var TodoModal = Backbone.View.extend({
        events:{
            "click .submit":"submit"
        },
        initialize:function () {
            //create and show the modal
            this.$el.html(TodoModalHtml)
            this.$(".modal").modal("show")
        },
        submit:function (eventName) {
            var input = this.$("#email").val(),
                that = this
            if (input.length > 0) {
                $.post("todos", {
                        email:_.escape(input)
                    }, function (data) {
                        that.$(".modal").modal("hide")
                    }
                )
            }
        }
    })

    return TodoModal
})