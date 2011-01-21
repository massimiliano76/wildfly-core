/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.threads;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADDRESS;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;
import static org.jboss.as.threads.Constants.BOUNDED_QUEUE_THREAD_POOL;
import static org.jboss.as.threads.Constants.QUEUELESS_THREAD_POOL;
import static org.jboss.as.threads.Constants.SCHEDULED_THREAD_POOL;
import static org.jboss.as.threads.Constants.THREAD_FACTORY;
import static org.jboss.as.threads.Constants.UNBOUNDED_QUEUE_THREAD_POOL;

import org.jboss.as.controller.Cancellable;
import org.jboss.as.controller.ModelAddOperationHandler;
import org.jboss.as.controller.NewOperationContext;
import org.jboss.as.controller.OperationHandler;
import org.jboss.as.controller.ResultHandler;
import org.jboss.dmr.ModelNode;

/**
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
class NewThreadsSubsystemAdd implements ModelAddOperationHandler {

    static final OperationHandler INSTANCE = new NewThreadsSubsystemAdd();

    /** {@inheritDoc} */
    @Override
    public Cancellable execute(NewOperationContext context, ModelNode operation, ResultHandler resultHandler) {

        context.getSubModel().get(BOUNDED_QUEUE_THREAD_POOL).setEmptyObject();
        context.getSubModel().get(QUEUELESS_THREAD_POOL).setEmptyObject();
        context.getSubModel().get(SCHEDULED_THREAD_POOL).setEmptyObject();
        context.getSubModel().get(THREAD_FACTORY).setEmptyObject();
        context.getSubModel().get(UNBOUNDED_QUEUE_THREAD_POOL).setEmptyObject();

        // Compensating is remove
        final ModelNode compensating = new ModelNode();
        compensating.get(OP_ADDR).set(operation.require(ADDRESS));
        compensating.get(OP).set("remove");

        resultHandler.handleResultComplete(compensating);

        return Cancellable.NULL;
    }
}
