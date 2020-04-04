import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.oodj.model.Admin;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.objects.NativeDebug;

public class Json {


    public static void main(String[] args) {
        try {
            Json json = new Json();
          //  json.serialize();
            json.deserialize();
            String path = "src\\main\\resources\\Admin.txt";
            List<Admin>adminRepository = new ArrayList<>();
            adminRepository.add(new Admin("a123w","asd","asdad","ccvcvxcv"));
            adminRepository.add(new Admin("aqw","asd","asdad","ccvcvxcv"));
            json.serialize1(adminRepository,path);
          adminRepository = json.deserialize1(path,Admin.class);
            System.out.println(adminRepository.get(0).getUsername());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void serialize() throws IOException {
        List<Admin>adminRepository = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        adminRepository.add(new Admin("a123w","asd","asdad","ccvcvxcv"));
        adminRepository.add(new Admin("aqw","asd","asdad","ccvcvxcv"));
        File file = new File("src\\main\\resources\\Admin.txt");
        if (file.createNewFile()) {
            System.out.println("File has been created.");
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(file,adminRepository);

    }

    private void deserialize(){
        List<Admin>adminRepository = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("src\\main\\resources\\Admin.txt"));
            adminRepository = mapper.readValue(br, new TypeReference<List<Admin>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("__>Type : " + adminRepository.get(0).getClass().getSimpleName());
        System.out.println(adminRepository.get(1).getName());
    }
     void serialize1(List<?> values,String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(path);
        if (file.createNewFile()) {
            System.out.println("File has been created.");
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(file,values);

    }

    public <E> List<E> deserialize1(String path, Class<?> tClass){
        ObjectMapper mapper = new ObjectMapper();

        JavaType type = mapper.getTypeFactory().
                constructCollectionType(
                        ArrayList.class,
                        tClass);
        List<E>adminRepository = new ArrayList<E>();
        E value;

        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(path));
            adminRepository =  mapper.readValue(br, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("__>Type : " + adminRepository.get(0).getClass().getSimpleName());
        return adminRepository;
    }
}
