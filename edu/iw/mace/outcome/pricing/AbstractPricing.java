/*
 * jCase - Java Combinatorial Auction Simulator 
 * Copyright (C) 2004-2006 Bjoern Schnizler, University of Karlsruhe (TH)
 * http://www.iw.uni-karlsruhe.de/jcase
 *
 * Parts of this work are funded by the European Union
 * under the IST project CATNETS (http://www.catnets.org/)
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of
 * the License, or any later version.
 * See the GNU General Public License for more details.
 *
 * This code comes WITHOUT ANY WARRANTY
 */
package edu.iw.mace.outcome.pricing;

import edu.iw.mace.environment.MaceException;
import edu.iw.mace.environment.Market;
import edu.iw.utils.patterns.AbstractProduct;

/**
 * This class represents the basic class for every pricing schema
 * 
 * Created: 25.09.2004
 * 
 * @author Bj�rn Schnizler, University of Karlsruhe (TH)
 * @version 1.1
 */
public abstract class AbstractPricing implements AbstractProduct {

	/**
	 * Empty constructor
	 */
	public AbstractPricing() {
		super();
	}

	/**
	 * Computes the prices for a given market instance
	 * 
	 * @param market
	 *            Market instance
	 * @throws MaceException
	 */
	public abstract void pricing(Market market) throws MaceException;

}
