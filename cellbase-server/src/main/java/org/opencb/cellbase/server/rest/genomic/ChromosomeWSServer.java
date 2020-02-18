/*
 * Copyright 2015-2020 OpenCB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opencb.cellbase.server.rest.genomic;

import io.swagger.annotations.*;
import org.opencb.biodata.models.core.Chromosome;
import org.opencb.cellbase.core.ParamConstants;
import org.opencb.cellbase.core.exception.CellbaseException;
import org.opencb.cellbase.core.result.CellBaseDataResult;
import org.opencb.cellbase.lib.SpeciesUtils;
import org.opencb.cellbase.lib.managers.GenomeManager;
import org.opencb.cellbase.server.exception.SpeciesException;
import org.opencb.cellbase.server.exception.VersionException;
import org.opencb.cellbase.server.rest.GenericRestWSServer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.List;

/**
 * @author imedina
 */
@Path("/{apiVersion}/{species}/genomic/chromosome")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Genome Sequence", description = "Genome Sequence RESTful Web Services API")
public class ChromosomeWSServer extends GenericRestWSServer {

    private GenomeManager genomeManager;

    public ChromosomeWSServer(@PathParam("apiVersion")
                              @ApiParam(name = "apiVersion", value = ParamConstants.VERSION_DESCRIPTION,
                                      defaultValue = ParamConstants.DEFAULT_VERSION) String apiVersion,
                              @PathParam("species")
                              @ApiParam(name = "species", value = ParamConstants.SPECIES_DESCRIPTION) String species,
                              @Context UriInfo uriInfo, @Context HttpServletRequest hsr)
            throws VersionException, SpeciesException, IOException, CellbaseException {
        super(apiVersion, species, uriInfo, hsr);
        if (assembly == null) {
            this.assembly = SpeciesUtils.getDefaultAssembly(cellBaseConfiguration, species).getName();
        }
        genomeManager = cellBaseManagerFactory.getGenomeManager(species, assembly);
    }

    @GET
    @Path("/model")
    @ApiOperation(httpMethod = "GET", value = ParamConstants.DATA_MODEL_DESCRIPTION,
            response = Chromosome.class, responseContainer = "QueryResponse")
    public Response getModel() {
        return createModelResponse(Chromosome.class);
    }

    @GET
    @Path("/search")
    @ApiOperation(httpMethod = "GET", value = "Retrieves all the chromosome objects", response = Chromosome.class,
        responseContainer = "QueryResponse")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "count", value = ParamConstants.COUNT_DESCRIPTION,
                    required = false, dataType = "boolean", paramType = "query", defaultValue = "false",
                    allowableValues = "false,true")
    })
    public Response getAll(@QueryParam("exclude") @ApiParam(value = ParamConstants.EXCLUDE_DESCRIPTION) String exclude,
                           @QueryParam("include") @ApiParam(value = ParamConstants.INCLUDE_DESCRIPTION) String include,
                           @QueryParam("sort") @ApiParam(value = ParamConstants.SORT_DESCRIPTION) String sort,
                           @QueryParam("limit") @DefaultValue("10") @ApiParam(value = ParamConstants.LIMIT_DESCRIPTION) Integer limit,
                           @QueryParam("skip") @DefaultValue("0") @ApiParam(value = ParamConstants.SKIP_DESCRIPTION)  Integer skip) {
        try {
            parseIncludesAndExcludes(exclude, include, sort);
            parseLimitAndSkip(limit, skip);
            parseQueryParams();
            CellBaseDataResult queryResults = genomeManager.info(queryOptions);
            return createOkResponse(queryResults);
        } catch (Exception e) {
            return createErrorResponse(e);
        }
    }

//    @GET
//    @Path("/list")
//    @Deprecated
//    @ApiOperation(httpMethod = "GET", value = "Retrieves the chromosomes names", response = CellBaseDataResult.class,
//        hidden = true)
//    public Response getChromosomes() {
//        try {
//            parseQueryParams();
//            GenomeDBAdaptor dbAdaptor = dbAdaptorFactory.getGenomeDBAdaptor(this.species, this.assembly);
//            QueryOptions options = new QueryOptions();
//            options.put("include", "chromosomes.name");
//            return createOkResponse(dbAdaptor.getGenomeInfo(options));
//        } catch (Exception e) {
//            return createErrorResponse(e);
//        }
//    }

    @GET
    @Path("/{chromosomeName}/info")
    @ApiOperation(httpMethod = "GET", value = "Retrieves chromosome data for specified chromosome names",
            response = Chromosome.class, responseContainer = "QueryResponse")
    public Response getChromosomes(@PathParam("chromosomeName") @ApiParam(name = "chromosomeName", value = ParamConstants.CHROMOSOMES,
                                                required = true) String chromosomeId,
                                   @QueryParam("exclude") @ApiParam(value = ParamConstants.EXCLUDE_DESCRIPTION) String exclude,
                                   @QueryParam("include") @ApiParam(value = ParamConstants.INCLUDE_DESCRIPTION) String include,
                                   @QueryParam("sort") @ApiParam(value = ParamConstants.SORT_DESCRIPTION) String sort) {
        try {
            parseIncludesAndExcludes(exclude, include, sort);
            parseQueryParams();
            List<CellBaseDataResult> queryResults = genomeManager.getChromosomes(queryOptions, chromosomeId);
            return createOkResponse(queryResults);
        } catch (Exception e) {
            return createErrorResponse(e);
        }
    }

//    @GET
//    @Path("/{chromosomeName}/size")
//    @Deprecated
//    @ApiOperation(httpMethod = "GET", value = "Not properly implemented - to be fixed",
//            response = Chromosome.class, responseContainer = "QueryResponse", hidden = true)
//    public Response getChromosomeSize(@PathParam("chromosomeName") String chromosomeId) {
//        try {
//            parseQueryParams();
//            GenomeDBAdaptor dbAdaptor = dbAdaptorFactory.getGenomeDBAdaptor(this.species, this.assembly);
//            QueryOptions options = new QueryOptions("include", "chromosomes.size");
//            List<String> chromosomeList = Splitter.on(",").splitToList(chromosomeId);
//            List<CellBaseDataResult> queryResults = new ArrayList<>(chromosomeList.size());
//            for (String chromosome : chromosomeList) {
//                queryResults.add(dbAdaptor.getChromosomeInfo(chromosome, options));
//            }
//            return createOkResponse(queryResults);
//        } catch (Exception e) {
//            return createErrorResponse(e);
//        }
//    }

    @GET
    @Path("/help")
    public Response help() {
        return createOkResponse("Usage:");
    }
}