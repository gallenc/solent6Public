#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.${tmfSpecPackageName}.simulator.api.impl;

public enum ServiceProblemStatus {

    Submitted, Rejected, Acknowledged, Held, Pending, Resolved, Closed, Cancelled;

}
