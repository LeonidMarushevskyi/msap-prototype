'use strict';

angular.module('msapApp')
    .controller('MailBoxCtrl', function ($scope, $state, MailBoxService, Contacts, chCustomScrollConfig) {

        $scope.$on("msapApp:updateDraftsCount", function(event, draftsCount) {
            $scope.draftsCount = draftsCount;
        });

        $scope.$on("msapApp:updateUnreadInboxCount", function(event, unreadInboxCount) {
            $scope.unreadInboxCount = unreadInboxCount;
        });

        $scope.$on("msapApp:updateUnreadDeletedCount", function(event, unreadDeletedCount) {
            $scope.unreadDeletedCount = unreadDeletedCount;
        });

        $scope.$on("msapApp:updateContactList", function() {
            $scope.updateContactList();
        });

        $scope.showFolders = 'ch-show-folders';
        $scope.linkFolders = 'ch-mobile-mailbox__nav-tab__link_active';

        $scope.changeFolders = function() {
            $scope.showFolders = 'ch-show-folders';
            $scope.linkFolders = 'ch-mobile-mailbox__nav-tab__link_active';
            $scope.showContacts = '';
            $scope.linkContacts= '';
        };

        $scope.changeContacts = function() {
            $scope.showContacts = 'ch-show-contacts';
            $scope.linkContacts = 'ch-mobile-mailbox__nav-tab__link_active';
            $scope.showFolders = '';
            $scope.linkFolders= '';
        };

        $scope.composeMail = function(contact) {
            angular.merge($state.params, {contact: contact});
            $state.go('ch-inbox.new-mail', {mailId: undefined});
        };

        $scope.filterByContact = function(contact) {
            angular.merge($state.params, {contact: contact});

            if ($state.current.name !== 'ch-inbox.messages') {
                $state.go('ch-inbox.messages', {directory: 'inbox'});
            } else {
                $state.go('ch-inbox.messages', {directory: $state.params.directory}, {reload: true});
            }
        };        

        $scope.inkRipple = function() {
            var parent, ink, d, x, y;
            $(".ch-ink-btn").click(function(e){

                parent = $(this).parent();
                if(parent.find(".ink").length == 0)
                    $(".ch-ink-btn").append("<span class='ink'></span>");

                ink = parent.find(".ink");
                ink.removeClass("animate");

                //set size of .ink
                if(!ink.height() && !ink.width()) {
                    d = Math.max(parent.outerWidth(), parent.outerHeight());
                    ink.css({height: d, width: d});
                }

                x = e.pageX - parent.offset().left - ink.width()/2;
                y = e.pageY - parent.offset().top - ink.height()/2;

                ink.css({top: y+'px', left: x+'px'}).addClass("animate");
            })
        }

        $scope.updateContactList = function() {
            Contacts.avl({}, function(result) {
                $scope.contacts = result;
            });
        };
        $scope.updateContactList();

        MailBoxService.receiveUnreadCounts();

        $scope.chCustomScrollConfig = chCustomScrollConfig;
    });
