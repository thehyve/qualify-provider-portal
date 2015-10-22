//= require jquery
//= require tables
//= require utils

if( typeof(QSP) == 'undefined' )
	QSP = {}


QSP.Developers = {
	init: function() {
		var titles = $( ".applications li h2" );

		// Enable clicking on webservice names to load data
		titles.on( "click", function() {
			var div = $(this).parent().find( ".applicationList" );

			if( !div.data( "loaded" ) ) {
				QSP.Developers.load(div, $(this));
			}

			return false;
		});

		// load data if there is only a single webservice available
		if( titles.length == 1 ) {
			titles.first().trigger( "click" );
		}
	},

	load: function(element, h2) {
		// Load data for the given URL
		var url = element.data( "url" );

		// Add spinner to the div
		h2.append( Utils.spinner() );

		// Clear the element (in case of reloading)
		element.empty();

		$.get(url)
			.done(function(data) {
				// Create a table with data
				var table = Table.create();
				table.find( "thead" ).append( Table.headerRow( [ "App name", "App description", "Status", "Plan", "Last updated", "" ] ) );

				var tbody = table.find( "tbody" );

				// Add a row for each developer
				$.each(data, function(idx, el) {
					tr = $("<tr>")
							.data( "application_id", el.id )
							.data( "urls", el.urls )
							.addClass( "state-" + el.state )
							.append( $("<td>").append( $( "<a href='#'>" ).text(el.name).on( "click", function() { QSP.Developers.showApplicationInfo(this); return false; } ) ) )
							.append( $("<td>").text( el.description ).addClass( "description ellipsis" ).attr( "title", el.description ) )
							.append( $("<td>").text( el.state ).addClass( "state" ) )
							.append( $("<td>").text( el.plan.name ).addClass( "application_plan" ) )
							.append( $("<td>").text( el.updated_at ) )
							.append( $("<td>") );

					// Add buttons based on the state
					var buttonTd = tr.find( "td:last" );

					switch( el.state ) {
						case "pending":
							buttonTd
								.append( $("<a href='#'>").text( "Accept").on( "click", function() { QSP.Developers.actions.acceptApplication(this); return false; } ) );
							break;
						case "live":
							buttonTd
								.append( $("<a href='#'>").text( "Suspend").on( "click", function() { QSP.Developers.actions.suspendApplication(this); return false; } ) );
							break;
						case "suspended":
							buttonTd
								.append( $("<a href='#'>").text( "Resume").on( "click", function() { QSP.Developers.actions.resumeApplication(this); return false; } ) );
							break;
					}

					tbody.append(tr)
				});

				// Attach the table to the element
				element.append(table);

				// Mark the element as being loaded
				element.data("loaded", true);
			})
			.fail(function(jqXHR) {
				Utils.message( "Error loading developer list for this webservice: " + jqXHR.responseText, "alert-danger" );
			})
			.always(function() {
				h2.find( ".spinner" ).remove();
			});
	},

	/**
	 * Shows detailed information about an application in a modal dialog
	 */
	showApplicationInfo: function(element) {
		var $element = $(element);
		var row = $element.closest( "tr" );
		var url = row.data( "urls" ).detailsLink;

		// Add spinner to the name
		$element.append( Utils.spinner() );

		// Create modal dialog
		var dialog = $('#modal-application-info');

		$.get(url)
			.done(function(data) {
				dialog.find( ".modal-title" ).text(data.name);

				// Create table with information
				var table = Table.create();
				var tbody = table.find( "tbody" );

				tbody.append( Table.row( [ "Application name", data.name] ) );
				tbody.append( Table.row( [ "Application description", data.description] ) );
				tbody.append( Table.row( [ "Application plan", data.plan.name ] ) );

				tbody.append( Table.row( [ " ", " " ] ) );

				tbody.append( Table.row( [ "Account name", data.account.org_name] ) );
				tbody.append( Table.row( [ "User email", data.account.users[0].email ] ) );
				tbody.append( Table.row( [ "Service plan", data.service_plan.name ] ) );

				// Add it to the dialog and show it
				dialog.find( ".modal-body" ).empty().append(table);
				dialog.modal('show')
			})
			.fail(function(jqXHR) {
				dialog.find( ".modal-title" ).text("Error");
				dialog.find( ".modal-body" ).text("Error loading developer list for this webservice: " + jqXHR.responseText);
				dialog.modal('show')
			})
			.always(function() {
				$element.find( ".spinner" ).remove();
			});
	},

	actions: {
		updateApplication: function(element, url, errorPrefix) {
			$.ajax({
				type: "PUT",
				url: url
			})
			.done(function() {
				// Reload the table
				var li = $(element).closest( "li" )
				li.find( ".applicationList" ).data( "loaded", false );
				li.find( "h2" ).trigger( "click" );

				// Show a message that the application was updated
				Utils.message( "The selected application was updated", "alert-success" );
			})
			.fail(function(jqXHR) {
				Utils.message( errorPrefix + jqXHR.responseText, "alert-danger" );
			});
		},

		acceptApplication: function(link) {
			var row = $(link).closest("tr");
			var url = row.data( 'urls' ).acceptLink;

			this.updateApplication(link, url, "Error accepting the application: " );
		},

		suspendApplication: function(link) {
			var row = $(link).closest("tr");
			var url = row.data( 'urls' ).suspendLink;

			this.updateApplication(link, url, "Error suspending the application: " );
		},

		resumeApplication: function(link) {
			var row = $(link).closest("tr");
			var url = row.data( 'urls' ).resumeLink;

			this.updateApplication(link, url, "Error resuming the application: " );
		},

	}
};

$(QSP.Developers.init);
