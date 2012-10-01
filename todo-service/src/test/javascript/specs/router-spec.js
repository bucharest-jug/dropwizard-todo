define([
    "backbone", "router"
], function (Backbone, Router) {

    var View = Backbone.View.extend({
        render:function () {
            this.$el.html("<p>fake view</p>")
            return this
        }
    })

    var view, router

    describe("router Application router", function () {

        beforeEach(function () {
            $("body").append('<div id="container"></div>')
            view = new View
            router = new Router
        })

        afterEach(function () {
            $("div#container").remove()
        })

        it("should show a view", function () {
            expect($("div#container").length).toBe(1)
            router.showView("#container", view)
            expect($("#container").text()).toContain("fake view")
        })

        it("should call 'close' of old views", function () {
            spyOn(view, "close")

            router.showView("#container", view)
            expect(view.close).not.toHaveBeenCalled()
            // it should close the old view
            router.showView("#container", new View)
            expect(view.close).toHaveBeenCalled()
        })
    })
})