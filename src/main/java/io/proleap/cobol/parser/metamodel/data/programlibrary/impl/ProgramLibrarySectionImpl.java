/*
 * Copyright (C) 2016, Ulrich Wolffgang <u.wol@wwu.de>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package io.proleap.cobol.parser.metamodel.data.programlibrary.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.proleap.cobol.Cobol85Parser.LibraryAttributeClauseFormat2Context;
import io.proleap.cobol.Cobol85Parser.LibraryDescriptionEntryContext;
import io.proleap.cobol.Cobol85Parser.LibraryDescriptionEntryFormat1Context;
import io.proleap.cobol.Cobol85Parser.LibraryDescriptionEntryFormat2Context;
import io.proleap.cobol.Cobol85Parser.LibraryEntryProcedureClauseFormat2Context;
import io.proleap.cobol.Cobol85Parser.LibraryIsCommonClauseContext;
import io.proleap.cobol.Cobol85Parser.LibraryIsGlobalClauseContext;
import io.proleap.cobol.Cobol85Parser.ProgramLibrarySectionContext;
import io.proleap.cobol.parser.metamodel.ProgramUnit;
import io.proleap.cobol.parser.metamodel.data.programlibrary.LibraryDescriptionEntry;
import io.proleap.cobol.parser.metamodel.data.programlibrary.LibraryDescriptionEntryExport;
import io.proleap.cobol.parser.metamodel.data.programlibrary.LibraryDescriptionEntryImport;
import io.proleap.cobol.parser.metamodel.data.programlibrary.ProgramLibrarySection;
import io.proleap.cobol.parser.metamodel.impl.CobolDivisionElementImpl;

public class ProgramLibrarySectionImpl extends CobolDivisionElementImpl implements ProgramLibrarySection {

	private final static Logger LOG = LogManager.getLogger(ProgramLibrarySectionImpl.class);

	protected final ProgramLibrarySectionContext ctx;

	protected List<LibraryDescriptionEntry> libraryDescriptionEntries = new ArrayList<LibraryDescriptionEntry>();

	protected Map<String, LibraryDescriptionEntry> libraryDescriptionEntriesSymbolTable = new HashMap<String, LibraryDescriptionEntry>();

	public ProgramLibrarySectionImpl(final ProgramUnit programUnit, final ProgramLibrarySectionContext ctx) {
		super(programUnit, ctx);

		this.ctx = ctx;
	}

	@Override
	public LibraryDescriptionEntryExport addLibraryDescriptionEntryExport(
			final LibraryDescriptionEntryFormat1Context ctx) {
		LibraryDescriptionEntryExport result = (LibraryDescriptionEntryExport) getASGElement(ctx);

		if (result == null) {
			final String name = determineName(ctx.libraryName());
			result = new LibraryDescriptionEntryExportImpl(name, programUnit, ctx);

			/*
			 * attributes
			 */
			if (ctx.libraryAttributeClauseFormat1() != null) {
				result.addExportAttribute(ctx.libraryAttributeClauseFormat1());
			}

			/*
			 * entry procedures
			 */
			if (ctx.libraryEntryProcedureClauseFormat1() != null) {
				result.addExportEntryProcedure(ctx.libraryEntryProcedureClauseFormat1());
			}

			libraryDescriptionEntries.add(result);
			libraryDescriptionEntriesSymbolTable.put(name, result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public LibraryDescriptionEntryImport addLibraryDescriptionEntryImport(
			final LibraryDescriptionEntryFormat2Context ctx) {
		LibraryDescriptionEntryImport result = (LibraryDescriptionEntryImport) getASGElement(ctx);

		if (result == null) {
			final String name = determineName(ctx.libraryName());
			result = new LibraryDescriptionEntryImportImpl(name, programUnit, ctx);

			/*
			 * global
			 */
			final LibraryIsGlobalClauseContext libraryIsGlobalClause = ctx.libraryIsGlobalClause();

			if (libraryIsGlobalClause != null) {
				result.addGlobalClause(libraryIsGlobalClause);
			}

			/*
			 * common
			 */
			final LibraryIsCommonClauseContext libraryIsCommonClause = ctx.libraryIsCommonClause();

			if (libraryIsCommonClause != null) {
				result.addCommonClause(libraryIsCommonClause);
			}

			/*
			 * attributes
			 */
			for (final LibraryAttributeClauseFormat2Context libraryAttributeClauseFormat2Context : ctx
					.libraryAttributeClauseFormat2()) {
				result.addImportAttribute(libraryAttributeClauseFormat2Context);
			}

			/*
			 * entry procedures
			 */
			for (final LibraryEntryProcedureClauseFormat2Context libraryEntryProcedureClauseFormat2Context : ctx
					.libraryEntryProcedureClauseFormat2()) {
				result.addImportEntryProcedure(libraryEntryProcedureClauseFormat2Context);
			}

			libraryDescriptionEntries.add(result);
			libraryDescriptionEntriesSymbolTable.put(name, result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public LibraryDescriptionEntry createLibraryDescriptionEntry(final LibraryDescriptionEntryContext ctx) {
		final LibraryDescriptionEntry result;

		if (ctx.libraryDescriptionEntryFormat1() != null) {
			result = addLibraryDescriptionEntryExport(ctx.libraryDescriptionEntryFormat1());
		} else if (ctx.libraryDescriptionEntryFormat2() != null) {
			result = addLibraryDescriptionEntryImport(ctx.libraryDescriptionEntryFormat2());
		} else {
			LOG.warn("unknown program library description entry {}", ctx);
			result = null;
		}

		return result;
	}

	@Override
	public List<LibraryDescriptionEntry> getLibraryDescriptionEntries() {
		return libraryDescriptionEntries;
	}

	@Override
	public LibraryDescriptionEntry getLibraryDescriptionEntry(final String name) {
		return libraryDescriptionEntriesSymbolTable.get(name);
	}
}
