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

	private static ConcurrentMap<String, SlaveMigrationOperator> slaves = null;
	public synchronized static ConcurrentMap<String, SlaveMigrationOperator> getSlaves() {
		if (slaves == null) slaves = new ConcurrentHashMap<>();
		return slaves;
	}
}
