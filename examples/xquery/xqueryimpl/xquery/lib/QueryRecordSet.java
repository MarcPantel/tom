/*
 * Copyright (c) 2004-2007, INRIA
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *  - Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  - Neither the name of the INRIA nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
//Source file: C:\\document\\codegen\\xquery\\lib\\QueryRecordSet.java

package xquery.lib;

import xquery.lib.data.Sequence;
import xquery.lib.data.type.XQueryString;

public class QueryRecordSet extends Sequence 
{
  //  private String name;
   
  /**
   * @roseuid 4110B632000D
   */
  public QueryRecordSet() 
  {
    
  }
   
  /**
   * @return int
   * @roseuid 410F8B2903B8
   */
  public int getRecordCount() 
  {
	return this.size();
  }
   
  /**
   * @param position
   * @return xquery.lib.QueryRecord
   * @roseuid 410F8B3603AD
   */
  public QueryRecord getRecord(int position) 
  {
	if (this.size() <= position) {
	  return null; 
	}
	else {
	  return (QueryRecord)(this.elementAt(position));
	}
	
  }
   
  /**
   * @param record
   * @roseuid 410F8B7F0394
   */
  public void addRecord(QueryRecord record) 
  {
	this.add(record);    
  }


  public void addAll(Object objects[])
  {
	for (int i=0; i<objects.length; i++) {
	  this.add(objects[i]);
	}
  }

}
