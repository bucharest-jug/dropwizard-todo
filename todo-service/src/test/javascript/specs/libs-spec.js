/**
 * Test for availability of JavaScript functions/libraries.
 * We test for a certain version. People need to be aware they can break stuff when upgrading.
 * TODO: find a way to test bootstrap.js version ?
 */

define([
    'underscore', 'jquery', 'backbone'
], function (_, $, Backbone) {

    describe('Test the libraries', function () {
        describe('underscore.js', function () {
            it('must be version 1.3.3', function () {
                expect(_.VERSION).toEqual('1.3.3')
            })
        })

        describe('jquery', function () {
            it('must be version 1.8.2', function () {
                expect(jQuery.fn.jquery).toEqual('1.8.2')
                expect(jQuery).toEqual($)
            })
        })

        describe('backbone', function () {
            it('must be version 0.9.2', function () {
                expect(Backbone.VERSION).toEqual('0.9.2')
            })
        })
    })
})