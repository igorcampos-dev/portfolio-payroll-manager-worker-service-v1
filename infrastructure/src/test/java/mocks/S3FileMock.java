package mocks;

import com.nexus.aws.model.S3File;

import java.util.ArrayList;
import java.util.List;

public class S3FileMock {

    public static List<S3File> ListS3FileMock(){
        List<S3File> s3Files = new ArrayList<>();
        s3Files.add(S3File.builder()
                .filename("teste.csv")
                .size(12)
                .build());

        return s3Files;
    }

}
