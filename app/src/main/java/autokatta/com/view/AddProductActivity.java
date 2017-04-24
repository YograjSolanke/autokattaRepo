package autokatta.com.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BrandsTagResponse;
import autokatta.com.response.CategoryResponse;
import autokatta.com.response.GetTagsResponse;
import autokatta.com.response.OtherBrandTagAddedResponse;
import autokatta.com.response.OtherTagAddedResponse;
import autokatta.com.response.ProductAddedResponse;
import retrofit2.Response;

/**
 * Created by ak-004 on 21/4/17.
 */

public class AddProductActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener {


    private ApiCall apiCall;
    String store_id, myContact;
    EditText productname, productprice, productdetails, producttype;
    MultiAutoCompleteTextView multiautotext, autoCategory, multiautobrand;
    Button save;
    String tagpart = "", tagid = "", brandtagpart = "", finalbrandtags = "";
    String idlist = "", product_id;
    boolean tagflag = false;
    final ArrayList<String> id = new ArrayList<String>();
    final ArrayList<String> tagname = new ArrayList<String>();
    final ArrayList<String> brandtagId = new ArrayList<>();
    final ArrayList<String> brandTags = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_fragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

        store_id = getIntent().getExtras().getString("store_id");


        productname = (EditText) findViewById(R.id.editproductname);
        productprice = (EditText) findViewById(R.id.editproductprice);
        productdetails = (EditText) findViewById(R.id.editproductdetails);
        save = (Button) findViewById(R.id.btnsave);
        producttype = (EditText) findViewById(R.id.editproducttype);
        multiautotext = (MultiAutoCompleteTextView) findViewById(R.id.multiautotext);
        autoCategory = (MultiAutoCompleteTextView) findViewById(R.id.multiautocategory);
        multiautobrand = (MultiAutoCompleteTextView) findViewById(R.id.multiautobrand);


        multiautotext.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multiautobrand.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        autoCategory.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        save.setOnClickListener(this);
        getCategory();
        getTags();
        getBrandTags();

        multiautotext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {


                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    //  Toast.makeText(HelloFormStuff.this, edittext.getText(), Toast.LENGTH_SHORT).show();
                    multiautotext.setText(multiautotext.getText().toString() + ",");
                    multiautotext.setSelection(multiautotext.getText().toString().length());
                    check();
                    return true;
                }
                return false;
            }
        });

        multiautotext.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                check();
//
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnsave:
                String name, price, details, tags, type, name1, price1, details1, type1, tags1, category, category1, brands, brands1;

                name = productname.getText().toString();
                price = productprice.getText().toString();
                details = productdetails.getText().toString();
                type = producttype.getText().toString();
                category = autoCategory.getText().toString();

                category = category.trim();
                if (category.endsWith(","))
                    category = category.substring(0, category.length() - 1);
                category = category.trim();

                String text = multiautotext.getText().toString();
                ArrayList<String> images = new ArrayList<String>();
                ArrayList<String> othertag = new ArrayList<String>();
                if (text.endsWith(","))
                    text = text.substring(0, text.length() - 1);
                System.out.println("txttttt=" + text);
                text = text.trim();


                String[] parts = text.split(",");

                for (int l = 0; l < parts.length; l++) {
                    System.out.println(parts[l]);
                    tagpart = parts[l].trim();
                    if (!tagpart.equalsIgnoreCase("") && !tagpart.equalsIgnoreCase(" "))
                        images.add(tagpart);
                    if (!tagname.contains(tagpart) && !tagpart.equalsIgnoreCase("") && !tagpart.equalsIgnoreCase(" ")) {
                        othertag.add(tagpart);
                        System.out.println("tag going to add=" + tagpart);
                        try {
                            addOtherTags();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("other categoryyyyyyyyyyyyyyyy=" + othertag);

                }
                System.out.println("tagname arrat before change***************" + tagname);

                getTags();

                for (int i = 0; i < images.size(); i++) {

                    for (int j = 0; j < tagname.size(); j++) {
                        if (images.get(i).toString().equalsIgnoreCase(tagname.get(j).toString()))
                            idlist = idlist + "," + id.get(j).toString();
                    }

                }


                if (!multiautotext.getText().toString().equalsIgnoreCase("") && idlist.length() > 0) {
                    idlist = idlist.substring(1);
                    System.out.println("substring idddddddddd=" + idlist);


                }
                if (tagflag) {
                    tagid = tagid.substring(1);
                    System.out.println("response tag iddddddddddddddd=" + tagid);
                    if (!idlist.equalsIgnoreCase(""))
                        idlist = idlist + "," + tagid;
                    else
                        idlist = tagid;
                    System.out.println("final idlist iddddddddddddddd=" + idlist);

                }


                ArrayList<String> tempbrands = new ArrayList<String>();


                String textbrand = multiautobrand.getText().toString();

                if (textbrand.endsWith(","))
                    textbrand = textbrand.substring(0, textbrand.length() - 1);

                textbrand = textbrand.trim();

                if (!textbrand.equals("")) {

                    String[] bparts = textbrand.split(",");
                    for (int o = 0; o < bparts.length; o++) {
                        brandtagpart = bparts[o].trim();
                        if (!brandtagpart.equals("") && !brandtagpart.equalsIgnoreCase(" "))
                            tempbrands.add(brandtagpart);
                        if (!brandTags.contains(brandtagpart) && !brandtagpart.equals("") && !brandtagpart.equalsIgnoreCase(" ")) {
                            System.out.println("brand tag going to add=" + brandtagpart);
                            try {
                                addOtherBrandTags(brandtagpart);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                    }
                }


                for (int n = 0; n < tempbrands.size(); n++) {
                    if (finalbrandtags.equals(""))
                        finalbrandtags = tempbrands.get(n);
                    else
                        finalbrandtags = finalbrandtags + "," + tempbrands.get(n);

                }


                //fields validation
                if (name.equals("") && price.equals("") && details.equals("") && type.equals("")) {

                    Toast.makeText(AddProductActivity.this, "Please Enter All details", Toast.LENGTH_SHORT).show();
                } else if (type.equals("")) {
                    producttype.setError("Enter Product Type");
                } else if (name.equals("")) {
                    productname.setError("Enter Product Name");
                } else if (price.equals("")) {
                    productprice.setError("Enter Product Price");
                } else if (details.equals("")) {
                    productdetails.setError("Enter Product details");
                } else if (category.equalsIgnoreCase("")) {
                    Toast.makeText(AddProductActivity.this, "Please Select Product Category", Toast.LENGTH_SHORT).show();
                } else {

                    createProduct(store_id, name, price, details, "", type, "", category, finalbrandtags);

                }


                break;

        }

    }

    private void createProduct(String store_id,
                               String product_name,
                               String price,
                               String product_details,
                               String product_tags,
                               String product_type,
                               String images,
                               String category,
                               String brandtags) {

        ApiCall mApiCall = new ApiCall(AddProductActivity.this, this);
        mApiCall.addProduct(store_id, product_name, price, product_details, product_tags, product_type, images, category, brandtags);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                if (response.body() instanceof CategoryResponse) {
                    CategoryResponse moduleResponse = (CategoryResponse) response.body();
                    final List<String> module = new ArrayList<String>();
                    if (!moduleResponse.getSuccess().isEmpty()) {
                        for (CategoryResponse.Success message : moduleResponse.getSuccess()) {
                            module.add(message.getTitle());
                        }
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(AddProductActivity.this, R.layout.addproductspinner_color, module);
                        autoCategory.setAdapter(dataadapter);
                    } else
                        CustomToast.customToast(AddProductActivity.this, getString(R.string.no_response));
                } else if (response.body() instanceof BrandsTagResponse) {
                    BrandsTagResponse brandsTagResponse = (BrandsTagResponse) response.body();
                    List<String> brands = new ArrayList<>();
                    if (!brandsTagResponse.getSuccess().isEmpty()) {
                        for (BrandsTagResponse.Success success : brandsTagResponse.getSuccess()) {
                            brands.add(success.getTag());
                        }
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(AddProductActivity.this, R.layout.addproductspinner_color, brands);
                        multiautobrand.setAdapter(dataadapter);
                    }
                } else if (response.body() instanceof GetTagsResponse) {
                    GetTagsResponse tagsResponse = (GetTagsResponse) response.body();
                    List<String> tags = new ArrayList<>();
                    if (!tagsResponse.getSuccess().isEmpty()) {
                        for (GetTagsResponse.Success success : tagsResponse.getSuccess()) {
                            tags.add(success.getTag());
                        }
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(AddProductActivity.this, R.layout.addproductspinner_color, tags);
                        multiautotext.setAdapter(dataadapter);
                    }
                } else if (response.body() instanceof OtherBrandTagAddedResponse) {
                    CustomToast.customToast(AddProductActivity.this, "Brand Tag added successfully");
                } else if (response.body() instanceof OtherTagAddedResponse) {
                    tagid = tagid + "," + ((OtherTagAddedResponse) response.body()).getSuccess().getTagID();
                    tagflag = true;
                } else if (response.body() instanceof ProductAddedResponse) {
                    CustomToast.customToast(AddProductActivity.this, "Product added successfully");
                }
            } else {
                CustomToast.customToast(AddProductActivity.this, getString(R.string._404));
            }
        } else {
            CustomToast.customToast(AddProductActivity.this, getString(R.string.no_response));
        }

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }

    public void check() {
        String text = multiautotext.getText().toString();
        System.out.println("texttttttttttttttttt" + text.substring(0, text.length() - 1));
        if (text.endsWith(","))
            text = text.substring(0, text.length() - 1);
        String[] parts = text.split(",");
        System.out.println("size of partssssssssssssssssss" + parts.length);
        if (parts.length > 5) {
            multiautotext.setError("You can add maximum five tags");
        }

    }


    /*
   Add Other Brand Tags...
    */
    private void addOtherBrandTags(String brandtagpart) {
        ApiCall mApiCall = new ApiCall(AddProductActivity.this, this);
        mApiCall.addOtherBrandTags(brandtagpart, "1");
    }

    /*
    Add Other Tags...
     */
    private void addOtherTags() {
        ApiCall mApiCall = new ApiCall(AddProductActivity.this, this);
        mApiCall._autoAddTags(tagpart, "1");
    }

    /*
    Get Tags...
     */
    private void getTags() {
        ApiCall mApiCall = new ApiCall(AddProductActivity.this, this);
        mApiCall._autoGetTags("1");
    }

    /*
    Get Brand Tags...
     */
    private void getBrandTags() {
        ApiCall mApiCall = new ApiCall(AddProductActivity.this, this);
        mApiCall.getBrandTags("1");
    }


    /*
    Get Module...
     */
    private void getCategory() {
        ApiCall mApiCall = new ApiCall(AddProductActivity.this, this);
        mApiCall.Categories("Product");
    }
}
