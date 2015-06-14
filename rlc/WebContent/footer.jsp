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
	    <script type="text/javascript">
      $( '#myTab a' ).click( function ( e ) {
        e.preventDefault();
        $( this ).tab( 'show' );
      } );

      $( '#moreTabs a' ).click( function ( e ) {
        e.preventDefault();
        $( this ).tab( 'show' );
      } );

      ( function( $ ) {
          // Test for making sure event are maintained
          $( '.js-alert-test' ).click( function () {
            alert( 'Button Clicked: Event was maintained' );
          } );
          fakewaffle.responsiveTabs( [ 'xs', 'sm' ] );
      } )( jQuery );

    </script>
    <script>
      (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
      m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
      })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

      ga('create', 'UA-17600125-2', 'openam.github.io');
      ga('send', 'pageview');
    </script>

		<!--/ scripts -->
	</body>
</html>