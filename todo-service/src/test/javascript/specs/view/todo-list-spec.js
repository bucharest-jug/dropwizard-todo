define([
    "underscore", "jquery", "model/todo", "view/todo-list"
], function (_, $, Todo, TodoListView) {

    describe("view/todo-list Todo List View", function () {

        var todos, view
        // load this just once as it's slow and hinders tests
        todos = new Todo.Collection
        todos.url = "spec/fixtures/todo-list.json"
        todos.fetch({async:true})

        beforeEach(function () {
            view = new TodoListView({
                collection:todos
            })
        })

        it("renders a table and a button", function () {
            expect(view.$("table").length).toBe(1)
            expect(view.$("button.create").length).toBe(1)
        })

        it("renders the fixture data", function () {
            view.render()
            //rendering takes some time
            waitsFor(function () {
                return(view.$(".todo").length == 1)
            }, "render the todo row", 1000)

            runs(function () {
                expect(view.$(".todo").length).toBe(1)
                expect(view.$(".todo").attr("id")).not.toBe("")
                expect(view.$(".todo-id").text()).toBe("Test")
                expect(view.$(".todo-email").text()).toBe("john@example.com")
                expect(view.$(".todo-created").text()).toBe("1348645333931")
                expect(view.$(".todo-items").text()).toBe("2")
                expect(view.$(".todo-options").length).toBe(1)
            })
        })

        it("has button.delete", function () {
            view.render()

            waitsFor(function () {
                return(view.$(".todo").length == 1)
            }, "render the todo row", 1000)

            runs(function () {
                // test some HTML elements with jQuery
                var $button = $(view.$(".todo-options .delete"))
                expect($button.length).toBe(1)
                expect($button.is("button.btn")).toBeTruthy()
                expect($button.is(".delete")).toBeTruthy()
            })
        })

        // test for click events
        it("click on button.delete calls deleteTodo function", function () {
            var $todo = view.render().$("button.delete")
            expect($todo.length).toBe(1)

            //deleteTodo calls
            spyOn(Backbone, "sync").andReturn("deleted")
            $todo.trigger("click")
            expect(Backbone.sync).toHaveBeenCalled()
        })

    })
})