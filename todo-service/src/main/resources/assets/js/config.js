// set the require.js configuration for your application
require.config({
    baseUrl:"assets",

    paths:{
        // libraries
        "jquery":"js/libs/jquery-1.8.2.min",
        "underscore":"js/libs/underscore-min",
        "backbone":"js/libs/backbone-min",
        "bootstrap":"js/libs/bootstrap.min",
        // require.js text plugin
        "text":"js/libs/text",
        // paths for our code:
        "router":"js/router",
        "model":"js/model",
        "view":"js/view",
        "tpl":"tpl"
    },
    // 'shim' js libs not friendly with AMD specification
    shim:{
        underscore:{
            exports:"_"
        },
        backbone:{
            deps:[ "underscore", "jquery"],
            exports:"Backbone"
        }
    }
})

// this will bootstrap our application
require([
    "backbone", "router"
], function (Backbone, Router) {

    var app = new Router()
    Backbone.history.start({
        // routes without hashes
        pushState:true
    })
})

