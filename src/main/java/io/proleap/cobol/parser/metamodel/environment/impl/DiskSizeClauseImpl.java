/*
 * Copyright (C) 2016, Ulrich Wolffgang <u.wol@wwu.de>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package io.proleap.cobol.parser.metamodel.environment.impl;

import io.proleap.cobol.Cobol85Parser.DiskSizeClauseContext;
import io.proleap.cobol.parser.metamodel.CobolDivision;
import io.proleap.cobol.parser.metamodel.ProgramUnit;
import io.proleap.cobol.parser.metamodel.environment.DiskSizeClause;
import io.proleap.cobol.parser.metamodel.impl.CobolDivisionElementImpl;

public class DiskSizeClauseImpl extends CobolDivisionElementImpl implements DiskSizeClause {

	protected final DiskSizeClauseContext ctx;

	public DiskSizeClauseImpl(final ProgramUnit programUnit, final CobolDivision scope,
			final DiskSizeClauseContext ctx) {
		super(programUnit, scope, ctx);

		this.ctx = ctx;
	}

}