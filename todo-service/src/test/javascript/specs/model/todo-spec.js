define([
    "backbone", "model/todo"
], function (Backbone, Todo) {

    describe("model/todo Todo Model", function () {

        var todo

        beforeEach(function () {
            todo = new Todo.Model
            todo.url = "spec/fixtures/todo.json"
            todo.fetch({async:false})
        })

        it("loads from fixture file", function () {
            expect(todo.get("id")).toBe("Test")
            expect(todo.get("email")).toBe("john@example.com")
            expect(todo.get("created")).toBe(1348645333931)
            expect(todo.get("items").length).toBe(1)
        })

        it("returns a Collection of items", function () {
            var item,
                itemCollection = todo.getItemsAsCollection()
            expect(itemCollection.length).toBe(1)
            item = itemCollection.at(0)
            expect(item.get("title")).toBe("Item 1")
            expect(item.get("created")).toBe(1348645333931)
        })
    })

    describe("mode/todo Todo Collection", function () {
        var collection

        beforeEach(function () {
            collection = new Todo.Collection
            // load from files instead of netwok
            collection.url = "spec/fixtures/todo-list.json"
            // force sync fetch to avoid issues in tests
            collection.fetch({async:false})
        })

        it("loads data from the proper url", function () {
            expect((new Todo.Collection).url).toBe("/todos")
            expect(collection.length).toBe(1)
            expect(collection.at(0).get("id")).toBe("Test")
        })
    })
})