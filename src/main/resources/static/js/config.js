var Config = Config || {};
Config = function() {
	var self = this;
	
	this.signature;
	this.nonceStr;
	this.timestamp;
	this.appId;
	this.version = 2.0;
	
	//结尾不带斜线
	this.rootUrl = window.document.location.protocol + "//" + window.document.location.host;
	//开头带斜线，结尾不带斜线
	this.shortenedUrl = window.document.location.href.replace(self.rootUrl, "");
	//开头带斜线，结尾不带斜线
	this.contentPath = self.shortenedUrl.substring(0, self.shortenedUrl.substring(1).indexOf("/")+1);
	//结尾不带斜线
	this.baseUrl = self.rootUrl + self.contentPath;
	
	function getQueryString(){
		var query_string = {};
		var query = window.location.search.substring(1);
		var vars = query.split("&");
		for (var i=0;i<vars.length;i++) {
		  var pair = vars[i].split("=");
		  query_string[pair[0]] = pair[1];
		}
		return query_string;
	}
	
	function getQuery(id){
		var query = window.location.search.substring(1);
		var vars = query.split("&");
		for (var i=0;i<vars.length;i++) {
		  var pair = vars[i].split("=");
		  if(pair[0]===id){
			  return pair[1];
		  }
		}
		return "";
	}
	
	function getSysEnv(){
		var Sys = {};
		Sys.isIE = function(){
			return navigator.appName.indexOf("Microsoft Internet Explorer")!=-1 && document.all;
	    }
		Sys.isIE6 = function() {
	        return navigator.userAgent.split(";")[1].toLowerCase().indexOf("msie 6.0")=="-1"?false:true;
	    }
		Sys.isIE7 = function(){
	        return navigator.userAgent.split(";")[1].toLowerCase().indexOf("msie 7.0")=="-1"?false:true;
	    }
		Sys.isIE8 = function(){
	        return navigator.userAgent.split(";")[1].toLowerCase().indexOf("msie 8.0")=="-1"?false:true;
	    }
		Sys.isIE11 = function(){
	        return navigator.appName.indexOf("Netscape")!=-1;
	    }
		Sys.isNN = function(){
	        return navigator.userAgent.indexOf("Netscape")!=-1;
	    }
		Sys.isOpera = function(){
	        return navigator.appName.indexOf("Opera")!=-1;
	    }
		Sys.isFF = function(){
	        return navigator.userAgent.indexOf("Firefox")!=-1;
	    }
		Sys.isChrome = function(){
	        return navigator.userAgent.indexOf("Chrome") > -1;
	    }
		return Sys;
	}
	
	return {
		rootUrl: self.rootUrl,
		shortenedUrl: self.shortenedUrl,
		contentPath: self.contentPath,
		baseUrl: self.baseUrl,
		getQueryString: getQueryString,
		getQuery: getQuery,
		getSysEnv: getSysEnv,
		signature: self.signature,
		nonceStr: self.nonceStr,
	    timestamp: self.timestamp,
	    appId: self.appId,
		version: self.version
	};
}();