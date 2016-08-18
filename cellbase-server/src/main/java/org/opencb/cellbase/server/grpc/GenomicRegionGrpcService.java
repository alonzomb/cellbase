package org.opencb.cellbase.server.grpc;

import io.grpc.stub.StreamObserver;
import org.bson.Document;
import org.opencb.biodata.models.common.protobuf.service.ServiceTypesModel;
import org.opencb.biodata.models.core.protobuf.GeneModel;
import org.opencb.biodata.models.core.protobuf.RegulatoryRegionModel;
import org.opencb.biodata.models.core.protobuf.TranscriptModel;
import org.opencb.biodata.models.variant.protobuf.VariantProto;
import org.opencb.cellbase.core.api.DBAdaptorFactory;
import org.opencb.cellbase.core.api.GeneDBAdaptor;
import org.opencb.cellbase.core.api.TranscriptDBAdaptor;
import org.opencb.cellbase.server.grpc.service.GenericServiceModel;
import org.opencb.cellbase.server.grpc.service.GenomicRegionServiceGrpc;
import org.opencb.commons.datastore.core.Query;
import org.opencb.commons.datastore.core.QueryOptions;

import java.util.Iterator;
import java.util.List;

/**
 * Created by swaathi on 18/08/16.
 */
public class GenomicRegionGrpcService extends GenomicRegionServiceGrpc.GenomicRegionServiceImplBase implements IGrpcService{

    private DBAdaptorFactory dbAdaptorFactory;

    public GenomicRegionGrpcService(DBAdaptorFactory dbAdaptorFactory) {
        this.dbAdaptorFactory = dbAdaptorFactory;
    }

    @Override
    public void getGene(GenericServiceModel.Request request, StreamObserver<GeneModel.Gene> responseObserver) {
        GeneDBAdaptor geneDBAdaptor = dbAdaptorFactory.getGeneDBAdaptor(request.getSpecies(), request.getAssembly());

        Query query = createQuery(request);
        QueryOptions queryOptions = createQueryOptions(request);
        Iterator iterator = geneDBAdaptor.nativeIterator(query, queryOptions);
        while (iterator.hasNext()) {
            Document gene = (Document) iterator.next();
            responseObserver.onNext(ProtoConverterUtils.createGene(gene));
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getTranscript(GenericServiceModel.Request request, StreamObserver<TranscriptModel.Transcript> responseObserver) {
        TranscriptDBAdaptor transcriptDBAdaptor = dbAdaptorFactory.getTranscriptDBAdaptor(request.getSpecies(), request.getAssembly());
        Query query = createQuery(request);
        QueryOptions queryOptions = createQueryOptions(request);
        Iterator iterator = transcriptDBAdaptor.nativeIterator(query, queryOptions);
        int count = 0;
        int limit = queryOptions.getInt("limit", 0);
        while (iterator.hasNext()) {
            Document gene = (Document) iterator.next();
            List<Document> transcripts = (List<Document>) gene.get("transcripts");
            if (limit > 0) {
                for (int i = 0; i < transcripts.size() && count < limit; i++) {
                    responseObserver.onNext(ProtoConverterUtils.createTranscript(transcripts.get(i)));
                    count++;
                }
            } else {
                for (Document doc : transcripts) {
                    responseObserver.onNext(ProtoConverterUtils.createTranscript(doc));
                }
            }
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getVariation(GenericServiceModel.Request request, StreamObserver<VariantProto.Variant> responseObserver) {
        super.getVariation(request, responseObserver);
    }

    @Override
    public void getSequence(GenericServiceModel.Request request, StreamObserver<ServiceTypesModel.StringResponse> responseObserver) {
        super.getSequence(request, responseObserver);
    }

    @Override
    public void getRegulatoryRegion(GenericServiceModel.Request request, StreamObserver<RegulatoryRegionModel.RegulatoryRegion> responseObserver) {
        super.getRegulatoryRegion(request, responseObserver);
    }

    @Override
    public void getTfbs(GenericServiceModel.Request request, StreamObserver<RegulatoryRegionModel.RegulatoryRegion> responseObserver) {
        super.getTfbs(request, responseObserver);
    }
}
