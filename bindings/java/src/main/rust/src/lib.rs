extern crate tokenizers as tk;

use ::safer_ffi::prelude::*;

use tk::FromPretrainedParameters;
use tk::Tokenizer;

#[derive_ReprC]
#[ReprC::opaque]
pub struct FFITokenizer {
    tokenizer: Tokenizer,
}

impl FFITokenizer {

    pub fn from_pretrained(identifier: &str) -> FFITokenizer {
        let parameters = FromPretrainedParameters::default();
        let tk_result = Tokenizer::from_pretrained(identifier, Some(parameters)).expect("identifier not found");
        return FFITokenizer { tokenizer: tk_result }
    }

}

#[derive_ReprC]
#[repr(C)]
pub struct FFIEncoding {
    ids: repr_c::Vec<i64>,
    type_ids: repr_c::Vec<i64>,
    tokens: repr_c::Vec<char_p::Box>,
    words: repr_c::Vec<i64>,
}

#[ffi_export]
fn tokenizer_from_pretrained(ffi_identifier: char_p::Ref ) -> repr_c::Box<FFITokenizer> {
    let input = ffi_identifier.to_str();
    repr_c::Box::new(FFITokenizer::from_pretrained(input))
}

#[ffi_export]
fn tokenizer_encode(it: &FFITokenizer, ffi_input: char_p::Ref) -> repr_c::Box<FFIEncoding> {
    let input = ffi_input.to_str();
    let encoded = it.tokenizer.encode(input, false);
    match encoded {
        Err(_e) => panic!("encode failed"),
        Ok(encoding) => {
            let ids = encoding
                .get_ids()
                .iter()
                .map(|i| i64::from(*i))
                .collect::<Vec<_>>()
                .into();
            let type_ids = encoding
                .get_type_ids()
                .iter()
                .map(|i| i64::from(*i))
                .collect::<Vec<_>>()
                .into();
            let tokens = encoding
                .get_tokens()
                .iter()
                .map(|s| char_p::new(s.clone()))
                .collect::<Vec<_>>()
                .into();
            let words = encoding
                .get_word_ids()
                .iter()
                .map(|w| match w {
                    Some(v) => i64::from(*v),
                    None => -1, // to indicate null
                })
                .collect::<Vec<_>>()
                .into();

            repr_c::Box::new(FFIEncoding {
                ids,
                type_ids,
                tokens,
                words,
            })
        }
    }
}

#[ffi_export]
fn tokenizer_drop(ptr: repr_c::Box<FFITokenizer>) {
    drop(ptr);
}

#[ffi_export]
fn encoding_drop(ptr: repr_c::Box<FFIEncoding>) {
    drop(ptr);
}

/// The following test function is necessary for the header generation.
/// Headers are only needed during development. It helps inspecting the
/// needed JNA interface.
#[::safer_ffi::cfg_headers]
#[test]
fn generate_headers() -> ::std::io::Result<()> {
    ::safer_ffi::headers::builder()
        .to_file("tokenizers.h")?
        .generate()
}
