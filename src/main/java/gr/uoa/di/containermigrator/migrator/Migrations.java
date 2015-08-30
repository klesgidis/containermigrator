package gr.uoa.di.containermigrator.migrator;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class Migrations {
	private static ConcurrentMap<String, Migrator> migrations = null;
	public synchronized static ConcurrentMap<String, Migrator> getMigrations() {
		if (migrations == null) migrations = new ConcurrentHashMap<>();
		return migrations;
	}
}
