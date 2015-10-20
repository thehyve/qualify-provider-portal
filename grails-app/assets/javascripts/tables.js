//= require jquery
//= require table
var Table = {
	create: function() {
		return $( "<table>" )
				.addClass( "table table-striped" )
				.append( $( "<thead>" ) )
				.append( $( "<tbody>" ) )
				.append( $( "<tfoot>" ) );
	},

	headerRow: function(entries) {
		return this.row(entries, "th" );
	},

	row: function(entries, tagName) {
		if(!tagName)
			tagName = "td";

		return $( "<tr>" )
			.append( $.map(entries, function(el) {
				return $("<" + tagName + ">").text(el);
			})
		);
	}

}
