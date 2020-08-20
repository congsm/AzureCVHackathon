<html>
<head>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
<style>
.button {
  background-color: #4CAF50;
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 14px;
  margin: 4px 2px;
  cursor: pointer;
}
</style>
</head>
<body>
<div align="center" style="height: 800px; width: 1000px; display: inline-block;">

	<div style="white-space: nowrap";><img alt="" src="../AzureVisionWeb/images/azure-icon.png" style="height: 130px; width: 180px;"><img alt="" src="../AzureVisionWeb/images/cvs-health-logo.png" style="height: 130px; width: 180px;"></div>
	<div id="title" style="color:red"><h1>Welcome to Azure Computer Vision Hackathon!</h1></div>
	<div id="menu">
		<h1><u>Employee Recognition Application v0.1</u></h1>
	</div>
	<p/>
	<hr style="width: 580px;"/>
	<p/>
   	<div id="top" style="width: 550px;" align="left">
		<h2>IDEA:</h2>		
	</div>
	<div id="content" style="width: 550px;" align="left">
		<h3>Building Employee Recognition Application using The Microsoft Cognitive Services (Face API).</h3>
	</div>
	
	<div id="top" style="width: 550px;" align="left">
		<h2>OVERVIEW:</h2>		
	</div>
	<div id="content" style="width: 550px;">
	<table style="font-size: 120%;;">
		<tr><td>
			The purpose of this application is intend to explore the functionality of the Face API.

		</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>
			To build Employee Recognition Application, we need some cognitive services with which to detect and identify human faces in the image. 
			When using the Face API, we need to create a collection of employee, called a "PersonGroup". Each PersonGroup may contain up to 1000 or 10,000 
			employee(people). Each employee in the PersonGroup is represented by a 'Person' entity (each with its own PersonId), and each Person can have 
			up to 248 Faces (reference images associated with that Person entity). When all the faces and employees have been added to a PersonGroup, 
			there's one additional step to proceed. We need to call an additional API to "Train" the PersonGroup. Once the training is complete, 
			the service is ready to use.  All this preparation is needed before we get to the Employee Recognition application. The following steps demonstrates the process of building out a this application.
		</td></tr>
	</table>
	</div>
	<hr style="width: 580px;"/>
	<div id="content" style="width: 550px;"><a href="#" class="button">Next >></a></div>
</div>
</body>
</html>
