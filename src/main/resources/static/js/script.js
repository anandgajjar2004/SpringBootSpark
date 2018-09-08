var app = angular.module('angularAppGenerator', []);
app.controller('MainCotroller', function($scope, $http) {
	
	$scope.fields = 
	{
			"applicationName" : '', 
			"packageName" : '',
			"listOfEntities" : [{"name":"Student","listOfFields":[{"type":"String","fieldName":"firstName"},{"type":"String","fieldName":"lastName"}]},
								{"name":"User","listOfFields":[{"type":"String","fieldName":"firstName"},{"type":"String","fieldName":"lastName"}]}]			
			};
    $scope.createApp = function(fields)
    {
    	data = fields;
    	$http({
			method : 'POST',
			url : "/rest/generate/",
			data : data,			
		}).then(function successCallback(response) {
				if(response.data == 'Success')
				{
					 location.href="/rest/generate/zip/"+fields.applicationName;
				}
				return response;			   
		  }, function errorCallback(response) {
			  alert("Error");
		  });
    };	

    $scope.addEntity = function(fields)
    {
    	fields.listOfEntities.push({name: "", listOfFields : [{name: "", type: ""}]});
    };		
    
    $scope.addField = function(field)
    {    		
    	field.push({name: "", type: ""});
    };

    $scope.removePage = function(index)
    {
    	$scope.fields.pages.splice(index, 1);
    };		

    $scope.createDownloadLink = function(anchorSelector, str, fileName){        
        if(window.navigator.msSaveOrOpenBlob) {
            var fileData = [str];
            blobObject = new Blob(fileData);
            $(anchorSelector).click(function(){
                window.navigator.msSaveOrOpenBlob(blobObject, fileName);
            });
        } else {
            var url = "data:text/plain;charset=utf-8," + encodeURIComponent(str);
            $(anchorSelector).attr("download", fileName);               
            $(anchorSelector).attr("href", url);
        }
    }

});