(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('SettingsController', SettingsController);

    SettingsController.$inject = ['Principal', 'Auth', 'Store', 'Workroom'];

    function SettingsController (Principal, Auth, Store, Workroom) {
        var vm = this;

        vm.error = null;
        vm.save = save;
        vm.settingsAccount = null;
        vm.success = null;
        vm.stores = Store.query();
        vm.workrooms = Workroom.query();


        /**
         * Store the "settings account" in a separate variable, and not in the shared "account" variable.
         */
        var copyAccount = function (account) {
            return {
                activated: account.activated,
                email: account.email,
                firstName: account.firstName,
                langKey: account.langKey,
                lastName: account.lastName,
                login: account.login,
                //now add sitUser details to account
                companyId: account.companyId,
                workroomId: account.workroomId,
                storeId: account.storeId,
                fitterIndicator: account.fitterIndicator,
                managerApprovalCode: account.managerApprovalCode,
                userType: account.userType
            };
        };

        Principal.identity().then(function(account) {
            var cpAcct = copyAccount(account);
            console.log("acct:" + JSON.stringify(account));

            vm.settingsAccount = cpAcct;
        });

        function save () {
            Auth.updateAccount(vm.settingsAccount).then(function() {
                vm.error = null;
                vm.success = 'OK';
                Principal.identity(true).then(function(account) {
                    vm.settingsAccount = copyAccount(account);
                });
            }).catch(function() {
                vm.success = null;
                vm.error = 'ERROR';
            });
        }
    }
})();
