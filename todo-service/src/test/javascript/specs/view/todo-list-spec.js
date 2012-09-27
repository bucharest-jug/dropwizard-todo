define([
    "underscore", "jquery", "model/todo", "view/todo-list"
], function (_, $, Todo, TodoListView) {

    describe("view/todo-list Todo List View", function () {

        var todos, view

        beforeEach(function () {
            todos = new Todo.Collection
            todos.url = "spec/fixtures/todo-list.json"
            todos.fetch({async:true})
            view = new TodoListView({
                collection:todos
            })
        })

        it("renders a table", function () {
            view.render()
            expect(view.$el.html()).toBe("")
        })
    })
})