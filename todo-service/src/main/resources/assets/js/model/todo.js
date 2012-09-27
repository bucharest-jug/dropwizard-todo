define([
    "backbone"
], function (Backbone) {

    var Todo = {}

    Todo.Item = Backbone.Model.extend({
        defaults:function () {
            return {
                title:"",
                created:-1
            }
        }
    })

    Todo.Items = Backbone.Collection.extend({
        model:Todo.Item
    })

    Todo.Model = Backbone.Model.extend({
        // use a function to always return NEW objects
        defaults:function () {
            return {
                id:"",
                email:"",
                created:-1,
                items:[]
            }
        },
        getItemsAsCollection:function () {
            return new Todo.Items(this.get("items"))
        }
    })

    Todo.Collection = Backbone.Collection.extend({
        model:Todo.Model,
        url:"/todos"
    })

    // always return the object that you export or else face undefined consequences
    return Todo
})