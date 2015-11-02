var Utils = {
	message: function(message, className) {
		if( !className )
			className = "alert-info";

		$('h1' ).first().after(
			$( '<div role="alert"></div>' )
				.addClass( "alert fade in alert-dismissible" ).addClass(className)
				.text(message)
				.prepend( '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' )
			);
	},

	spinner: function() {
    	return '<span class="glyphicon glyphicon-refresh glyphicon-spin spinner"></span>';
    }
};