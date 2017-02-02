(function() {
    'use strict';

    angular
        .module('sitApp')
        .factory('Principal', Principal);

    Principal.$inject = ['$q', 'Account', 'User', '$rootScope'];

    function Principal ($q, Account, User, $rootScope) {
        var _identity,
            _authenticated = false;

        var service = {
            authenticate: authenticate,
            hasAnyAuthority: hasAnyAuthority,
            hasAuthority: hasAuthority,
            identity: identity,
            isAuthenticated: isAuthenticated,
            isIdentityResolved: isIdentityResolved
        };

        return service;

        function authenticate (identity) {
            _identity = identity;
            _authenticated = identity !== null;
        }

        function hasAnyAuthority (authorities) {
            if (!_authenticated || !_identity || !_identity.authorities) {
                return false;
            }

            for (var i = 0; i < authorities.length; i++) {
                if (_identity.authorities.indexOf(authorities[i]) !== -1) {
                    return true;
                }
            }

            return false;
        }

        function hasAuthority (authority) {
            if (!_authenticated) {
                return $q.when(false);
            }

            return this.identity().then(function(_id) {
                return _id.authorities && _id.authorities.indexOf(authority) !== -1;
            }, function(){
                return false;
            });
        }

        function identity (force) {
            var deferred = $q.defer();

            if (force === true) {
                _identity = undefined;
            }

            // check and see if we have retrieved the identity data from the server.
            // if we have, reuse it by immediately resolving
            if (angular.isDefined(_identity)) {
                deferred.resolve(_identity);

                //move this to somewhere after the promise fulfilled if possible.
                if (typeof $rootScope.companyTitleName === "undefined"
                    || $rootScope.companyTitleName === "undefined") {
                    User.getSitUserDetails({username: _identity.login}, function (result) {
                        $rootScope.companyTitleName = result.company.description;
                        $rootScope.storeTitleName = result.store.description;
                        $rootScope.workroomTitleName = result.workroom.description;
                    });
                }
                return deferred.promise;
            }

            // retrieve the identity data from the server, update the identity object, and then resolve.
            Account.get().$promise
                .then(getAccountThen)
                .catch(getAccountCatch);

            return deferred.promise;

            function getAccountThen (account) {
                _identity = account.data;
                _authenticated = true;

                //user successfully logged in, now update navbar with company/store/workroom
                User.getSitUserDetails({username:_identity.login}, function(result){
                    $rootScope.companyTitleName = result.company.description;
                    $rootScope.storeTitleName = result.store.description;
                    $rootScope.workroomTitleName = result.workroom.description;
                });

                deferred.resolve(_identity);
            }

            function getAccountCatch () {
                _identity = null;
                _authenticated = false;
                deferred.resolve(_identity);
            }
        }

        function isAuthenticated () {
            return _authenticated;
        }

        function isIdentityResolved () {
            return angular.isDefined(_identity);
        }
    }
})();
