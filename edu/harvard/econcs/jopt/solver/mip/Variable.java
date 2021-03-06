/*
 * Copyright (c) 2005
 *	The President and Fellows of Harvard College.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the University nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE UNIVERSITY AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE UNIVERSITY OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */


/*
 * Created on Apr 13, 2004
 *
 */
package edu.harvard.econcs.jopt.solver.mip;

import java.io.Serializable;

import edu.harvard.econcs.jopt.solver.MIPException;

/**
 * Basic MIP Variable.
 * 
 * @author Last modified by $Author: jeffsh $
 * @version $Revision: 1.5 $ on $Date: 2005/10/17 18:49:44 $
 * @since Apr 13, 2004
 */

public class Variable implements Serializable, Cloneable {
	private static final long serialVersionUID = 45646456456l;
	private String name;
	private double lowerBound;
	private double upperBound;
	private VarType type;
	private boolean ignore = false;
	
	/**
	 * @param name
	 * @param upperBound
	 * @param lowerBound
	 * @param type
	 */
	public Variable(String name, VarType type, double lowerBound, double upperBound) {
		super();
		MIP.checkMax(lowerBound);
		if (lowerBound > upperBound) {
			throw new MIPException("Lowerbound must be less than upperBound");
		}
		this.name = name;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.type = type;
	}

	
	/**
	 * @return Returns the lowerBound.
	 */
	public double getLowerBound() {
		return lowerBound;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return Returns the type.
	 */
	public VarType getType() {
		return type;
	}
	/**
	 * @return Returns the upperBound.
	 */
	public double getUpperBound() {
		return upperBound;
	}

	public String toString() {
		String ret=getName();
		if (ret == null) {
			ret = "Var";
		}
		return ret;
	}	
	
	public String toStringPretty() {
		String ret=getName();
		if (ret == null) {
			ret = "Var";
		}
		ret += " {" + getType() + ", " + getLowerBound() + ", " + getUpperBound() +"}";
		return ret;
	}
	
	/**
	 * @param ignore To ignore this variable during MIP solving. Only WDResult
	 * Determination people should be using this right now.
	 */
	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}
	
	public boolean ignore() {
		return ignore;
	}
	
	protected Object clone() throws CloneNotSupportedException {
		Variable ret = (Variable)super.clone();		
		return ret;
	}
	
	public Variable typedClone() {
		try {
			return (Variable)clone();
		} catch (CloneNotSupportedException e) {
			throw new MIPException("Problem in clone", e);
		}
	}
}
