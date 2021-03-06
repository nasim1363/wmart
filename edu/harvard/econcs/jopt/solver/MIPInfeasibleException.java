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


package edu.harvard.econcs.jopt.solver;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import edu.harvard.econcs.jopt.solver.mip.Constraint;
import edu.harvard.econcs.jopt.solver.mip.Variable;

/**
 * Exception that is thrown when evaluating a MIP, that contains the infeasible set information
 * 
 * @author Benjamin Lubin; Last modified by $Author: blubin $
 * @version $Revision: 1.5 $ on $Date: 2006/08/22 18:40:03 $
 * @since Apr 19, 2005
 **/
public class MIPInfeasibleException extends MIPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3618981187340022066L;
	/** Variables to Cause objects **/
	Map infeasibleVariables = new HashMap();
	/** Constraints to Cause objects **/
	Set infeasibleConstraints = new HashSet();
	
	public MIPInfeasibleException(Map infeasibleVariables, Set infeasibleConstraints) {
		super("MIP Infeasible");
		this.infeasibleVariables = infeasibleVariables;
		this.infeasibleConstraints = infeasibleConstraints;
	}

	public Set getInfeasibleVariables() {
		return Collections.unmodifiableSet(infeasibleVariables.keySet());
	}
	
	public Cause getCause(Variable v) {
		return (Cause)infeasibleVariables.get(v);
	}
	
	public Set getInfeasibleConstraints() {
		return Collections.unmodifiableSet(infeasibleConstraints);
	}
	
	public String getCauseDescription() {
		StringBuffer sb = new StringBuffer();
		sb.append(getVariableDescription()).append("\n").append(getConstraintDescription());
		return sb.toString();
	}
	
	protected String getVariableDescription() {
		StringBuffer sb = new StringBuffer("Variables causing infeasibility:\n");
		Set inf = getInfeasibleVariables();
		Variable[] vars = (Variable[])inf.toArray(new Variable[inf.size()]);
		Arrays.sort(vars, new Comparator(){
			public int compare(Object o1, Object o2) {
				return ((Variable)o1).getName().compareTo(((Variable)o2).getName());
			}});
		for (int i=0; i<vars.length; i++) {
			Variable v=vars[i];
			sb.append(v).append(" pushed to ").append(getCause(v)).append("\n");
		}
		return sb.toString();
	}
	
	protected String getConstraintDescription() {
		StringBuffer sb = new StringBuffer("Constraints causing infeasibility:\n");
		for (Iterator iter = getInfeasibleConstraints().iterator(); iter.hasNext(); ) {
			Constraint c = (Constraint)iter.next();
			if (c==null) {
				sb.append("NULL CONSTRAINT?!");
			} else {
				sb.append(c.getId()).append(": ").append(c.prettyString()).append("\n");
			}
		}
		return sb.toString();
		
	}
	
	public String getMessage() {
		return super.getMessage() + "\n" + getCauseDescription();
	}
	
	public static class Cause implements Serializable {
		public static Cause LOWER = new Cause(1);
		public static Cause UPPER = new Cause(2);
		private static final long serialVersionUID = 200504191645l;
		private int type;
		
		public Cause(int type) {
			this.type = type;
		}
		
		public boolean equals(Object o) {
			return o != null && o.getClass().equals(Cause.class) && ((Cause)o).type == type;
		}
		
		public int hashCode() {
			return type;
		}
		
		public String toString() {
	        switch(type) {
	        	case 1:
	        		return "Lower";
	        	case 2:
	        		return "Upper";
	        }
	        return "Unknown:ERROR";
		}
		
		/** Make serialization work. **/
		private Object readResolve () throws ObjectStreamException
	    {
	        switch(type) {
	        	case 1:
	        		return LOWER;
	        	case 2:
	        		return UPPER;
	        }
	        throw new InvalidObjectException("Unknown type: " + type);
	    }

	}
}
