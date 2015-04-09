<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tuna TÖRE</title>
        <script type="text/javascript">
			var xhr = getXMLHttpRequest();
		    function sendRequest()
		    {
		    	var action = "ptn";
				var login = 18311;
				var pwd = 10072014;
				var req = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <message> <serviceRequest serviceCode=\"getCountries\"> <serviceParameters> <brand code =\"EP\"/> </serviceParameters> </serviceRequest> </message>";
		    	var url="https://applications-ptn.europcar.com/xrs/resxml";
		    	if (action=="bck")
		        {
		    		url="https://applications-bck.europcar.com/xrs/resxml";
		    	}
		    	else if (action=="ptn")
		        {
		    		url="https://applications-ptn.europcar.com/xrs/resxml";
		    	}
		    	else if (action=="tst")
		        {
		    		url="https://applications-tst.europcar.com/xrs/resxml";
		    	}
		    	else if (action=="prod")
		        {
		    		url="https://applications.europcar.com/xrs/resxml";
		    	}
		    	document.getElementById('resp').value = "Waiting for server response...";
		    	
		    	var xhr = getXMLHttpRequest();
		    	xhr.onreadystatechange=function()
		    	{ 
		    		if(xhr.readyState == 4)
		        	{
		    			if(xhr.status == 200)
		    			{  	
		    				var content = xhr.responseText;
		    				// console.log(content);
		    				if (content.length > 0)
		        			{
		    					document.getElementById('RESP').value= content;
		    					return;
		    				}
		    			}
		    		}
		    	}; 
		    	xhr.open("POST", "pruebaws.jsp", true);		
		    	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		    	var data = "action="+escape(action)+"&login="+login+"&pwd="+pwd+"&req="+req+"&url="+url;  
		    	xhr.send(data);
		    }
		
		    function testEnv(env)
		    {
		    	var login = document.getElementById('login');
		    	var pwd = document.getElementById('pwd');
		    	if(env == 'prod'){
		    		login.value = "";
		    		pwd.value = "";
		    		
		    	} else {
		    		login.value = "320880";
		    		pwd.value = "04042008";
		    	}
		    }
		    function getXMLHttpRequest()
		    {
		    	if (window.XMLHttpRequest)
				{
		    		return new XMLHttpRequest();
		    	}
		    	else
			    {
		    		if (window.ActiveXObject)
			    	{
		    			try
		    			{
		    				return new ActiveXObject("Msxml2.XMLHTTP");
		    			}
		    			catch (e)
		    			{
		    				try
		    				{
		    					return new ActiveXObject("Microsoft.XMLHTTP");
		    				}
		    				catch (e)
		    				{
		    					return NULL;
		    				}
		    			}
		    		}
		    	}
			} 
		</script>
    </head>
    <body>
        <h1>Testing XMLHttpRequest Object</h1>
        <body>
            <div id="data"><!--XML Data will be here--></div> <br/>
            <button type="button" onclick="sendRequest()">Test XMLHttpRequest Object</button>
            <input type="text" id="resp" name="resp" value=""/>
</body>
    </body>
</html>