package com.jk.earn.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.jk.earn.R;
import com.jk.earn.adapter.PhoneEmptyAdapter;
import com.jk.earn.net.bean.PhoneProduct;

public class PhoneProductEmptyView extends FrameLayout {

	public PhoneProductEmptyView(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.view_phone_empty, this);
		GridView mGridView = (GridView) view
				.findViewById(R.id.view_phone_empty_gv);

		List<PhoneProduct> products = new ArrayList<PhoneProduct>();

		PhoneProduct product1 = new PhoneProduct();
		product1.setCardWorth(1);

		PhoneProduct product2 = new PhoneProduct();
		product2.setCardWorth(2);

		PhoneProduct product3 = new PhoneProduct();
		product3.setCardWorth(5);

		PhoneProduct product4 = new PhoneProduct();
		product4.setCardWorth(10);

		PhoneProduct product5 = new PhoneProduct();
		product5.setCardWorth(20);

		PhoneProduct product6 = new PhoneProduct();
		product6.setCardWorth(50);

		products.add(product1);
		products.add(product2);
		products.add(product3);

		products.add(product4);
		products.add(product5);
		products.add(product6);

		PhoneEmptyAdapter mAdapter = new PhoneEmptyAdapter(context, products);
		mGridView.setAdapter(mAdapter);
	}
}
