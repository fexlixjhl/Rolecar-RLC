
<!-- page footer -->
<footer class="page-footer">
		<div class="grid-row">
			<!-- social nav -->
			<div class="social-nav">							
				<a href="https://twitter.com/rolecar"><i class="fa fa-twitter"></i></a>
				<a href="https://www.facebook.com/rolecardrive"><i class="fa fa-facebook"></i></a>
				<a href="https://plus.google.com/u/0/100151209502610081833/posts"><i class="fa fa-google-plus"></i></a>		
			</div>
	</div>
	<div class="page-footer-section">
		<div class="grid-row">
			<!-- copyrights -->
			<div class="copyrights"><fmt:message key="footer.copyright"/></div>
			<!--/ copyrights -->
			
			<!-- secondary nav -->
			<div class="secondary-nav">
				<a href="principalinicial.jsp"><fmt:message key="footer.back"/></a>
			
			</div>
			<!--/ secondary nav -->
		</div>
	</div>
</footer>
<!--/ page footer -->
	
</div>
<script>
var getElementsByClassName=function(a,b,c){if(document.getElementsByClassName){getElementsByClassName=function(a,b,c){c=c||document;var d=c.getElementsByClassName(a),e=b?new RegExp("\\b"+b+"\\b","i"):null,f=[],g;for(var h=0,i=d.length;h<i;h+=1){g=d[h];if(!e||e.test(g.nodeName)){f.push(g)}}return f}}else if(document.evaluate){getElementsByClassName=function(a,b,c){b=b||"*";c=c||document;var d=a.split(" "),e="",f="http://www.w3.org/1999/xhtml",g=document.documentElement.namespaceURI===f?f:null,h=[],i,j;for(var k=0,l=d.length;k<l;k+=1){e+="[contains(concat(' ', @class, ' '), ' "+d[k]+" ')]"}try{i=document.evaluate(".//"+b+e,c,g,0,null)}catch(m){i=document.evaluate(".//"+b+e,c,null,0,null)}while(j=i.iterateNext()){h.push(j)}return h}}else{getElementsByClassName=function(a,b,c){b=b||"*";c=c||document;var d=a.split(" "),e=[],f=b==="*"&&c.all?c.all:c.getElementsByTagName(b),g,h=[],i;for(var j=0,k=d.length;j<k;j+=1){e.push(new RegExp("(^|\\s)"+d[j]+"(\\s|$)"))}for(var l=0,m=f.length;l<m;l+=1){g=f[l];i=false;for(var n=0,o=e.length;n<o;n+=1){i=e[n].test(g.className);if(!i){break}}if(i){h.push(g)}}return h}}return getElementsByClassName(a,b,c)},
dropdowns = document.getElementsByTagName( 'select' );
for ( i=0; i<dropdowns.length; i++ )
if ( dropdowns[i].className.match( 'dropdown-menu' ) ) dropdowns[i].onchange = function(){ if ( this.value != '' ) window.location.href = this.value; }
</script>


</body>


</html> 