<%@ include file="/WEB-INF/views/includes/taglibs.jsp" %>

							
		</div><!-- end: Content -->
	</div><!--/row-fluid-->
	<div class="modal hide fade" id="myModal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>Settings</h3>
		</div>
		<div class="modal-body">
			<p>Here settings can be configured...</p>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">Close</a>
			<a href="#" class="btn btn-primary">Save changes</a>
		</div>
	</div>
	<div class="clearfix"></div>
	<footer>
		<p>
			<span style="text-align:left;float:left">&copy; <a href="" target="_blank">creativeLabs</a> 2013</span>
			<span class="hidden-phone" style="text-align:right;float:right">Powered by: <a href="#">Acme Dashboard</a></span>
		</p>
	</footer>
	</div><!--/.fluid-container-->
	
<c:if test="${ !empty sessionScope.generalmessage }">
	<script type="text/javascript">
	$(document).ready(function() {
		noty({"text":"${sessionScope.generalmessage}","layout":"top","type":"error","closeButton":"true"});
	});
	</script>
	<c:set var="generalmessage" value="" scope="session"/>
</c:if>	

</body>



</html>