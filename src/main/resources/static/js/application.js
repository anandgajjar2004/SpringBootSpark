var app = angular.module('angularAppGenerator', []);
app.controller('MainCotroller', function($scope, $http) {
	
	$scope.fields = 
	{
			"applicationName" : 'demo', 
			"packageName" : 'com.example',
			"entity" : {"name" : "User", "listOfFields":[{"type":"String","fieldName":"firstName"},{"type":"","fieldName":""}]}						
			};
    $scope.createApp = function(fields)
    {
    	if(fields.applicationName == '') {
    		alert("Please Provide Application Name");
    		return;
    	}
    	
    	if(fields.packageName == '') {
    		alert("Please Provide Package Name");
    		return;
    	}
    	
    	if(fields.entity.name == '') {
    		alert("Please Provide Entity Name");
    		return;
    	}
    	
    	for(var i=0;i<fields.entity.listOfFields.length;i++) {
    		if(fields.entity.listOfFields[i].type == '') {
    			alert("Please Provide Type at Position "+(i+1));
    			return;
    		}
    		if(fields.entity.listOfFields[i].fieldName == '') {
    			alert("Please Provide Field Name at Position "+(i+1));
    			return;
    		}
    		
    		
    	}
    	
    	
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
  
    $scope.addField = function(field)
    {    		
    	field.push({name: "", type: ""});
    };

    $scope.deleteField = function(index)
    {
    	$scope.fields.entity.listOfFields.splice(index, 1);
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