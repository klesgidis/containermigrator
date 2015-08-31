package gr.uoa.di.containermigrator.migrator;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class Migrations {
	private static ConcurrentMap<String, MigrationOperator> migrations = null;
	public synchronized static ConcurrentMap<String, MigrationOperator> getMigrations() {
		if (migrations == null) migrations = new ConcurrentHashMap<>();
		return migrations;
	}
}
