/*
 * Class: ProdutoController
 * Description: Controller for the Product entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 29/08/2024
 * Last Updated: 29/08/2024
 */
package blomera.praceando.praceandoapipg.controller;

import blomera.praceando.praceandoapipg.model.Produto;
import blomera.praceando.praceandoapipg.repository.ProdutoRepository;
import blomera.praceando.praceandoapipg.service.FirebaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/productss")
public class ProdutoController {

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadProdutoImage(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
        try {
            // Faz o upload da imagem e obtém a URL
            String imageUrl = firebaseStorageService.uploadFile(file);


            // testeeeeee
            // Atualiza o produto com a nova URL
            Produto products = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            products.setUrlImagem(imageUrl);
            produtoRepository.save(products);

            return ResponseEntity.ok("Upload realizado com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao fazer upload da imagem");
        }
    }
}

