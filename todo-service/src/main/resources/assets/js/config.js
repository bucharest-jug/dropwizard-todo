// set the require.js configuration for your application
require.config({
    // initialize the application with the main application file
    deps:[ "main" ],

    baseUrl:"assets/js",

    paths:{
        // libraries
        "jquery":"libs/jquery-1.8.2.min",
        "underscore":"libs/underscore-min",
        "backbone":"libs/backbone-min",
        "bootstrap":"libs/bootstrap.min"
    },
    // js libs not friendly with AMD specification
    shim:{
        underscore:{
            exports:"_"
        },
        backbone:{
            deps:[ "underscore", "jquery"],
            exports:"Backbone"
        }
    }
});
