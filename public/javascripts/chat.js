var app = angular.module('chatApp', ['ngMaterial']);

app.controller('chatController', function ($scope) {
    $scope.messages = [
        {
            'sender': 'USER',
            'text': 'Hello'
		},
        {
            'sender': 'BOT',
            'text': 'Hi!!What can I do for you'
		},
        {
            'sender': 'USER',
            'text': 'Can you play some music!!'
		},
        {
            'sender': 'BOT',
            'text': 'Ok!!!Playing...'
		}
	];
});