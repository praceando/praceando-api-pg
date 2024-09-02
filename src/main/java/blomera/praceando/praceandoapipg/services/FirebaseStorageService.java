/*
 * Class: FirebaseStorageService
 * Description: Service class for handling file uploads to Firebase Storage.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 29/08/2024
 * Last Updated: 29/08/2024
 */
package blomera.praceando.praceandoapipg.services;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FirebaseStorageService {

    public String uploadFile(MultipartFile file) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.create(file.getOriginalFilename(), file.getBytes(), file.getContentType());
        return blob.getMediaLink();
    }
}

