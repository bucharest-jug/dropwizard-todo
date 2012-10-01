define([
    "view/todo-modal"
], function (TodoModal) {

    describe("view/todo-modal", function () {

        var view

        beforeEach(function () {
            view = new TodoModal().render()
        })

        it("renders a modal", function () {
            expect(view.$(".modal").length).toBe(1)
            expect(view.$("#email").length).toBe(1)
            expect(view.$(".submit").length).toBe(1)
        })
    })
})