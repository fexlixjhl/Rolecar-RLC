
<!-- footer amp-->
<footer class="amp-footer">
	<div class="wraper wraper-margin-horizon-respon amp-footer-contenido">
		<div class="col-xs-12 col-sm-12">
			<a class= "amp-footer-contenido-home"href="principalinicial.jsp"><i class="fa fa-home"></i></a>
			<div class="amp-footer-contenido-social">
				<a href="https://twitter.com/rolecar"><i class="fa fa-twitter"></i></a>
				<a href="https://www.facebook.com/rolecardrive"><i class="fa fa-facebook"></i></a>
				<a href="https://plus.google.com/u/0/100151209502610081833/posts"><i class="fa fa-google-plus"></i></a>
			</div>
			<div class= amp-footer-contenido-copyright>
				<span >
					<fmt:message key="footer.copyright"/>
				</span>
			</div>
		</div>

	</div>
</footer>


<script>
var getElementsByClassName=function(a,b,c){if(document.getElementsByClassName){getElementsByClassName=function(a,b,c){c=c||document;var d=c.getElementsByClassName(a),e=b?new RegExp("\\b"+b+"\\b","i"):null,f=[],g;for(var h=0,i=d.length;h<i;h+=1){g=d[h];if(!e||e.test(g.nodeName)){f.push(g)}}return f}}else if(document.evaluate){getElementsByClassName=function(a,b,c){b=b||"*";c=c||document;var d=a.split(" "),e="",f="http://www.w3.org/1999/xhtml",g=document.documentElement.namespaceURI===f?f:null,h=[],i,j;for(var k=0,l=d.length;k<l;k+=1){e+="[contains(concat(' ', @class, ' '), ' "+d[k]+" ')]"}try{i=document.evaluate(".//"+b+e,c,g,0,null)}catch(m){i=document.evaluate(".//"+b+e,c,null,0,null)}while(j=i.iterateNext()){h.push(j)}return h}}else{getElementsByClassName=function(a,b,c){b=b||"*";c=c||document;var d=a.split(" "),e=[],f=b==="*"&&c.all?c.all:c.getElementsByTagName(b),g,h=[],i;for(var j=0,k=d.length;j<k;j+=1){e.push(new RegExp("(^|\\s)"+d[j]+"(\\s|$)"))}for(var l=0,m=f.length;l<m;l+=1){g=f[l];i=false;for(var n=0,o=e.length;n<o;n+=1){i=e[n].test(g.className);if(!i){break}}if(i){h.push(g)}}return h}}return getElementsByClassName(a,b,c)},
dropdowns = document.getElementsByTagName( 'select' );
for ( i=0; i<dropdowns.length; i++ )
if ( dropdowns[i].className.match( 'dropdown-menu' ) ) dropdowns[i].onchange = function(){ if ( this.value != '' ) window.location.href = this.value; }

</script>
<%-- <script src="http://www.rolecar.com/wp-includes/js/comment-reply.min.js?ver=3.9.1" type="text/javascript">
var addComment={moveForm:function(a,b,c,d){var e,f=this,g=f.I(a),h=f.I(c),i=f.I("cancel-comment-reply-link"),j=f.I("comment_parent"),k=f.I("comment_post_ID");if(g&&h&&i&&j){f.respondId=c,d=d||!1,f.I("wp-temp-form-div")||(e=document.createElement("div"),e.id="wp-temp-form-div",e.style.display="none",h.parentNode.insertBefore(e,h)),g.parentNode.insertBefore(h,g.nextSibling),k&&d&&(k.value=d),j.value=b,i.style.display="",i.onclick=function(){var a=addComment,b=a.I("wp-temp-form-div"),c=a.I(a.respondId);if(b&&c)return a.I("comment_parent").value="0",b.parentNode.insertBefore(c,b),b.parentNode.removeChild(b),this.style.display="none",this.onclick=null,!1};try{f.I("comment").focus()}catch(l){}return!1}},I:function(a){return document.getElementById(a)}};
</script> --%>

<!-- /copyright -->
<!-- /footer -->

</body>
<script>
// $("#tvhiculo").buttonset();
// $("#tsucursal").buttonset();
// $(".seleccion").selectmenu();
</script>

</html> 