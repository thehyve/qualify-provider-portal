databaseChangeLog = {

	changeSet(author: "robert (generated)", id: "1415374036695-1") {
		createTable(tableName: "qualify_user_webservices") {
			column(name: "webservice_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "int8") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "robert (generated)", id: "1415374036695-6") {
		addPrimaryKey(columnNames: "user_id, webservice_id", tableName: "qualify_user_webservices")
	}

	changeSet(author: "robert (generated)", id: "1415374036695-7") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "qualify_user_webservices", constraintName: "FK_2o5fr551vriiwh49ryf81v0dg", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "qualify_user", referencesUniqueColumn: "false")
	}

	changeSet(author: "robert (generated)", id: "1415374036695-8") {
		addForeignKeyConstraint(baseColumnNames: "webservice_id", baseTableName: "qualify_user_webservices", constraintName: "FK_6x9esbaxhkv6hujjbqn0toyda", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "webservice", referencesUniqueColumn: "false")
	}
}
