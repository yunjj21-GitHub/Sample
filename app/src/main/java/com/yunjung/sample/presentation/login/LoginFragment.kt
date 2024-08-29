package com.yunjung.sample.presentation.login

import androidx.fragment.app.viewModels
import com.yunjung.sample.R
import com.yunjung.sample.base.BaseFragment
import com.yunjung.sample.databinding.FragmentLoginBinding

class LoginFragment: BaseFragment<FragmentLoginBinding>(
    R.layout.fragment_login
) {
    override val viewModel: LoginViewModel by viewModels()

    override fun initView() { }

    override fun initObserver() {
        super.initObserver()
    }
}