extern crate tokenizers as tk;

use ::safer_ffi::prelude::*;

use tk::Tokenizer;
use tk::FromPretrainedParameters;

#[derive_ReprC]
#[ReprC::opaque]
pub struct FFITokenizer {
    tokenizer: Tokenizer
}

#[ffi_export]
fn tokenizer_new() -> repr_c::Box<FFITokenizer>
{
    let identifier = "setu4993/LaBSE";
    let parameters = FromPretrainedParameters::default();
    let tk_result = Tokenizer::from_pretrained(identifier, Some(parameters));
    match tk_result {
        Err(_e) => panic!("identifier not found"),
        Ok(v) => repr_c::Box::new(FFITokenizer { tokenizer: v }),
    }
}

#[ffi_export]
fn tokenizer_encode(it: &FFITokenizer) //-> repr_c::Box<FFIEncoding>
//fn tokenizer_encode(it: &FFITokenizer, input: repr_c::String) -> repr_c::Box<FFIEncoding>
{
    let input = "hello I'm viet";
    let encoded = it.tokenizer.encode(input, false);
    match encoded {
        Err(_e) => panic!("encode failed"),
        Ok(encoding) => {
            let foo: Vec<repr_c::String> = encoding.get_tokens().into_iter().map(|s| repr_c::String::from(s.clone())).rev().collect();
            let bar: Vec<Option<repr_c::Box<u32>>> = encoding.get_word_ids().into_iter()
                .map(|x| x.map(|y| repr_c::Box::new(y)))
                .rev().collect();

            repr_c::Box::new(FFIEncoding {
                ids: encoding.get_ids().to_vec().into_boxed_slice().into(),
                type_ids: encoding.get_type_ids().to_vec().into_boxed_slice().into(),
                tokens: foo.into_boxed_slice().into(),
                words: bar.into_boxed_slice().into(),
            });
        },
    }
}

#[ffi_export]
fn tokenizer_drop(ptr: repr_c::Box<FFITokenizer>)
{
    drop(ptr);
}

#[derive_ReprC]
#[repr(C)]
pub struct FFIEncoding {
    ids: c_slice::Box<u32>,
    type_ids: c_slice::Box<u32>,
    tokens: c_slice::Box<repr_c::String>,
    words: c_slice::Box<Option<repr_c::Box<u32>>>,
}

#[ffi_export]
fn encoding_drop(ptr: repr_c::Box<FFIEncoding>) 
{
    drop(ptr);
}

/// The following test function is necessary for the header generation. 
/// Headers are only needed during development. It helps inspecting the 
/// needed JNA interface.
#[::safer_ffi::cfg_headers]
#[test]
fn generate_headers () -> ::std::io::Result<()>
{
    ::safer_ffi::headers::builder()
        .to_file("tokenizers.h")?
        .generate()
}
